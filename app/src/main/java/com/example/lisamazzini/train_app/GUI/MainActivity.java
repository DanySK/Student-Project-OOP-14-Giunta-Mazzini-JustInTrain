package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyAdder;
import com.example.lisamazzini.train_app.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    List<String> list = new LinkedList<>();
    FavouriteJourneyAdder adder = new FavouriteJourneyAdder();
    ArrayAdapter aAdpt;
    ListView lv;
    SpinnerAdapter spinnerAdapter;


    private android.app.ActionBar actionBar;
    private ArrayList<String> list2;
    ActionBar.OnNavigationListener navigationListener;


    private String departure;
    private String arrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        adder.setContext(MainActivity.this);
        lv = (ListView) findViewById(R.id.favListView);
        aAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);



        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        Map<String, Set<String>> map = (Map<String, Set<String>>) adder.getFavourites();
        List<String> lstr = new LinkedList<>();
        for (String str : map.keySet()) {
            for (String s : map.get(str)) {
                lstr.add(s);
            }
            list.add(lstr.get(0) + "_" + lstr.get(1));
            lstr.removeAll(lstr);
        }

        lv.setAdapter(aAdpt);

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

    public void restoreActionBar() {
        ActionBar action = getSupportActionBar();

        spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
        navigationListener = new ActionBar.OnNavigationListener() {
            String[] strings = getResources().getStringArray(R.array.action_list);
            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                Log.d("cazzi", "hai premuto " + i + " " + strings[i]);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
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

}
