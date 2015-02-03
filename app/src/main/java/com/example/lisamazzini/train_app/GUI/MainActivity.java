package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.lisamazzini.train_app.Controller.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController2;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Network.JourneyRestClient;
import com.example.lisamazzini.train_app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;


    ArrayList<String> journeys;
    ArrayAdapter<String> arrayAdapter;


    FavouriteJourneyController favouriteJourneyController;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ActionBar.OnNavigationListener navigationListener;
    JourneyResultsFragment2 fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        favouriteJourneyController = new FavouriteJourneyController(MainActivity.this);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment2.newInstance());
        fragment = (JourneyResultsFragment2)getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreActionBar() {
        journeys = new ArrayList<String>(favouriteJourneyController.getFavourites());
        final ArrayList<String> finalJourneys = new ArrayList<String>();
        final List<List<String>> realJourneys = new LinkedList<>();
        for (String s : journeys) {
            String[] splitted = s.split("_");
            String departure = splitted[0];
            String arrival = splitted[1];
            realJourneys.add(new LinkedList<String>(Arrays.asList(departure, arrival)));
            String finalString = "";
            for (String s1 : splitted) {
                finalString = finalString.concat(s1).concat(" ");
            }
            finalJourneys.add(finalString);
        }
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, finalJourneys);
        spinnerAdapter = arrayAdapter;

        ActionBar action = getSupportActionBar();
        navigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                fragment.makeRequests();
                return true;
            }
        };
        action.setDisplayShowTitleEnabled(false);
        action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
        action.setListNavigationCallbacks(spinnerAdapter, navigationListener);
    }


    public static class PlaceholderFragment extends Fragment {
        public static PlaceholderFragment newInstance() {
            return new PlaceholderFragment();
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    public static class JourneyResultsFragment2 extends Fragment {



        private Soluzioni train = null;
        private LinearLayoutManager manager;
        private JourneyResultsAdapter journeyResultsAdapter;
        private List<Soluzioni> flatJourneyTrainsList = new LinkedList<>();

        public static JourneyResultsFragment2 newInstance() {
            return new JourneyResultsFragment2();
        }

        public JourneyResultsFragment2() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);
            RecyclerView recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);

            this.manager = new LinearLayoutManager(getActivity());
            this.journeyResultsAdapter = new JourneyResultsAdapter(this.flatJourneyTrainsList);

            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(journeyResultsAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            return layoutInflater;
        }

        public void makeRequests() {
            final JourneyResultsController2 journeyController = new JourneyResultsController2("7104", "5066", "2015-02-01", "00:00:00");
            JourneyRestClient.get().getJourneys("7104", "5066", "2015-02-01T00:00:00", new Callback<Tragitto>() {
                @Override
                public void success(Tragitto journeys, Response response) {
                        flatJourneyTrainsList = journeyController.refillJourneyList(flatJourneyTrainsList, journeys.getSoluzioni());
                        journeyResultsAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println("erore");
                }
            });
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }
}