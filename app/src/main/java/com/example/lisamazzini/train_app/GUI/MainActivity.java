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
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.JourneyListWrapper;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.JourneyTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;


    ArrayList<String> journeys;
    ArrayAdapter<String> arrayAdapter;


    FavouriteJourneyController favouriteJourneyController;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ActionBar.OnNavigationListener navigationListener;
    JourneyResultsFragment2 fragment;

    private String departure;
    private String arrival;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

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
//        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
        navigationListener = new ActionBar.OnNavigationListener() {
            //            String[] strings = getResources().getStringArray(R.array.action_list);
            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                fragment.makeRequests(realJourneys.get(i).get(0), realJourneys.get(i).get(1));
                Log.d("cazzi", "hai premuto " + i + " " + finalJourneys.get(i));
                return true;
            }
        };
        action.setDisplayShowTitleEnabled(false);
        action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
        action.setListNavigationCallbacks(spinnerAdapter, navigationListener);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
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

        private JourneyResultsController journeyController;
        private final SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

        private JourneyTrain train = null;
        private RecyclerView recyclerView;
        private LinearLayoutManager manager;
        private JourneyResultsAdapter journeyResultsAdapter;
        List<JourneyTrain> flatJourneyTrainsList = new LinkedList<>();
        private List<List<JourneyTrain>> journeyTrains = new ArrayList<>(Constants.N_TIME_SLOT);

        private String departure;
        private String arrival;

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
            for (int i = 0; i < 5; i++) {
                journeyTrains.add(new LinkedList<JourneyTrain>());
            }
            recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);
            this.journeyResultsAdapter = new JourneyResultsAdapter(this.flatJourneyTrainsList);
            this.manager = new LinearLayoutManager(getActivity());
            recyclerView.setAdapter(journeyResultsAdapter);
            recyclerView.setLayoutManager(manager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            return layoutInflater;
        }

        public void makeRequests(String departure, String arrival) {
            journeyController = new JourneyResultsController(departure, arrival);
            for (int i = 0; i < Constants.N_TIME_SLOT; i++) {
                spiceManager.execute(journeyController.iterateTimeSlots(), new JourneyResultsRequestListener());
            }
        }

        private class JourneyResultsRequestListener implements RequestListener<JourneyListWrapper> {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Toast.makeText(getActivity(),
                        "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onRequestSuccess(final JourneyListWrapper journeys) {
                if (journeyController.newDataIsPresent(journeys.getList())) {
                    flatJourneyTrainsList = journeyController.refillJourneyList(flatJourneyTrainsList, journeys.getList(), journeys.getTimeSlot());
                    int pos = manager.findFirstCompletelyVisibleItemPosition();
                    journeyResultsAdapter.notifyDataSetChanged();

                    train = journeyController.getFirstTakeableTrain(journeys);

                    if (train != null) {
                        manager.scrollToPositionWithOffset(flatJourneyTrainsList.indexOf(train), 0);
                        train = null;
                    } else {
                        if (journeyController.newDataisInsertedBefore(journeys)) {
                            manager.scrollToPositionWithOffset(pos + journeys.getList().size(), 0);
                        } else {
                            manager.scrollToPositionWithOffset(pos, 0);
                        }
                    }
                }
            }
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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }
}