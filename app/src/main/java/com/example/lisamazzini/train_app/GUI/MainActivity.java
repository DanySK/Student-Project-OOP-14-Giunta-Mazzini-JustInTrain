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
import com.example.lisamazzini.train_app.Controller.JourneyDataRequest;
import com.example.lisamazzini.train_app.Controller.JourneyRequest;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController2;
import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

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

        private RecyclerView recyclerView;
        private LinearLayoutManager manager;
        private JourneyResultsAdapter journeyResultsAdapter;
        private List<PlainSolution> flatJourneyTrainsList = new LinkedList<>();
        JourneyResultsController2 controller;
        private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

        private String departureStation = "pesaro";
        private String departureID;
        private String arrivalStation = "cesenasdf";
        private String arrivalID;

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
            recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);

            this.manager = new LinearLayoutManager(getActivity());
            this.journeyResultsAdapter = new JourneyResultsAdapter(this.flatJourneyTrainsList);

            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(journeyResultsAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            return layoutInflater;
        }

        public void makeRequests() {
            spiceManager.execute(new JourneyDataRequest(departureStation), new DepartureDataRequestListenter());
            controller = new JourneyResultsController2("7104", "5066", "2015-02-02", "00:00:00");

        }

        private class DepartureDataRequestListenter implements RequestListener<String> {

            @Override
            public void onRequestFailure(SpiceException spiceException) {
                if (spiceException.getCause() instanceof InvalidStationException) {
                    Log.d("cazzi", "sbagliata partenza");
                }
            }

            @Override
            public void onRequestSuccess(String s) {
                departureID = s.split("\\|S")[1];
                spiceManager.execute(new JourneyDataRequest(arrivalStation), new ArrivalDataRequestListener());
            }
        }

        private class ArrivalDataRequestListener implements RequestListener<String> {

            @Override
            public void onRequestFailure(SpiceException spiceException) {
                if (spiceException.getCause() instanceof InvalidStationException) {
                    Log.d("cazzi", "sbagliata arrivo");
                }
            }

            @Override
            public void onRequestSuccess(String s) {
                arrivalID = s.split("\\|S")[1];
                spiceManager.execute(new JourneyRequest(departureID, arrivalID), new JourneyRequestListener());
            }
        }

        private class JourneyRequestListener implements RequestListener<Tragitto> {

            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.d("cazzi", "altro errore");
            }

            @Override
            public void onRequestSuccess(Tragitto tragitto) {
                controller.buildPlainSolutions(tragitto);
                journeyResultsAdapter = new JourneyResultsAdapter(controller.getPlainSolutions());
                recyclerView.setAdapter(journeyResultsAdapter);
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onStart() {
            spiceManager.start(getActivity());
            super.onStart();
        }

        @Override
        public void onStop() {
            spiceManager.shouldStop();
            super.onStop();
        }
    }

}