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
import android.widget.EditText;
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

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

//    private JourneyResultsController journeyController;
//    private final SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
//
//    private JourneyTrain train = null;
//    private RecyclerView recyclerView;
//    private LinearLayoutManager manager;
//    private JourneyResultsAdapter journeyResultsAdapter;
//    List<JourneyTrain> flatJourneyTrainsList = new LinkedList<>();
//    private List<List<JourneyTrain>> journeyTrains = new ArrayList<>(Constants.N_TIME_SLOT);

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
        favouriteJourneyController.removeAll();
        favouriteJourneyController.setAsFavourite("Pesaro", "Cesena");
        favouriteJourneyController.setAsFavourite("Bologna", "Cesena");
        favouriteJourneyController.setAsFavourite("Venezia", "Bologna");


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
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            Log.d("--------------", departure);
            String arrival = splitted[1];
            realJourneys.add(new LinkedList<String>(Arrays.asList(departure, arrival)));
            String finalString = "";
            for (String s1 : splitted) {
                Log.d("cazzi", s1);
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
//                Log.d("cazzi", "hai premuto " + i + " " + strings[i]);
                return true;
            }
        };
        action.setDisplayShowTitleEnabled(false);
        action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
//        action.setListNavigationCallbacks(arrayAdapter, navigationListener);
        action.setListNavigationCallbacks(spinnerAdapter, navigationListener);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
    }


    public static class JourneyResultsFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            EditText v = new EditText(getActivity());
            v.setText("Hello");
            return v;
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
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
            JourneyResultsFragment2 fragment = new JourneyResultsFragment2();
            return fragment;
        }

        public JourneyResultsFragment2() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);

//            Intent intent = getIntent();
//            departure = intent.getStringExtra("journeyDeparture");
//            arrival = intent.getStringExtra("journeyArrival");
//            departure = "cesena";
//            arrival = "pesaro";

//            journeyController = new JourneyResultsController(departure, arrival);

            for (int i = 0; i < 5; i++) {
                journeyTrains.add(new LinkedList<JourneyTrain>());
            }

            recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);
            this.journeyResultsAdapter = new JourneyResultsAdapter(this.flatJourneyTrainsList);
            this.manager = new LinearLayoutManager(getActivity());
            recyclerView.setAdapter(journeyResultsAdapter);
            recyclerView.setLayoutManager(manager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //  TODO listener per salvare i preferiti con favouriteController
//            makeRequests(departure, arrival);
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
//                // TODO fai che quando è nel tuo stesso timeslot controlli ogni treno con il primo buono da prendere e si posizioni su quello
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

        // TODO fai che quando esci dall'activity ferma robospice e quando riprende riprende pure lui

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
