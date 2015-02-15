package com.example.lisamazzini.train_app.GUI.Fragment;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lisamazzini.train_app.GUI.Activity.JourneyResultsActivity;
import com.example.lisamazzini.train_app.GUI.Activity.StationListActivity;
import com.example.lisamazzini.train_app.GUI.Adapter.DrawerListAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;

import org.apache.commons.lang3.text.WordUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;



    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;


    private Calendar calendar = Calendar.getInstance();
    private Format formatter = new SimpleDateFormat(Constants.SDF_NO_SECS);
    protected int hour = 0;
    private int minute = 0;
    private int day;
    private int month;
    private int year;
    private String actualTime;
    private boolean isCustomTime;

    private RecyclerView recyclerView;
    private DrawerListAdapter drawerListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private Menu menu;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View drawerView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        final EditText trainNumber = (EditText)drawerView.findViewById(R.id.eTrainNumber);
        final ImageButton trainNumberSearchButton = (ImageButton) drawerView.findViewById(R.id.bTrainNumberSearch);
        final EditText departure = (EditText)drawerView.findViewById(R.id.eDepartureStation);
        final EditText arrival = (EditText)drawerView.findViewById(R.id.eArrivalStation);
        final ImageButton journeySearchButton = (ImageButton) drawerView.findViewById(R.id.bJourneySearch);

        setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        setTime(calendar.get(Calendar.HOUR_OF_DAY)-1, calendar.get(Calendar.MINUTE), false);
        actualTime = buildDateTime();

        trainNumberSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trainNumber.length() > 0) {
                    Intent i = new Intent(getActivity(), StationListActivity.class);
                    i.putExtra(Constants.TRAIN_N_EXTRA, Utilities.trimAndCapitalizeString(trainNumber.getText().toString()));
                    startActivity(i);
                }
            }
        });

        journeySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (departure.length() > 0 && arrival.length() > 0) {
                    Intent i = new Intent(getActivity(), JourneyResultsActivity.class);
                    i.putExtra(Constants.DEPARTURE_STAT_EXTRA, Utilities.trimAndCapitalizeString(departure.getText().toString()));
                    i.putExtra(Constants.ARRIVAL_STAT_EXTRA, Utilities.trimAndCapitalizeString(arrival.getText().toString()));
                    i.putExtra(Constants.REQUESTED_TIME_EXTRA, buildDateTime());
                    i.putExtra(Constants.IS_CUSTOM_TIME_EXTRA, isCustomTime);
                    startActivity(i);
                }
            }
        });

        String[] TITLES = {"Treni preferiti", "Rimuovi treni preferiti", "Achievement"};

        recyclerView = (RecyclerView) drawerView.findViewById(R.id.lDrawerList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        drawerListAdapter = new DrawerListAdapter(TITLES);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(drawerListAdapter);

        return drawerView;
    }



    public boolean getCustomTime() {
        return this.isCustomTime;
    }

    public String getActualTime() {
        return this.actualTime;
    }

    public void setTime(int hour, int minute, boolean isCustomTime) {
        this.hour = hour;
        this.minute = minute;
        this.isCustomTime = isCustomTime;
    }

    public void setDate(int year, int month, int day, boolean isCustomTime) {
        this.year = year;
        this.day = day;
        this.month = month;
        this.isCustomTime = isCustomTime;
    }

    public String buildDateTime() {
        calendar.set(this.year, this.month, this.day, this.hour, this.minute);
        return formatter.format(calendar.getTime());
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.menu_main, menu);
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            ActionBar action = ((ActionBarActivity) getActivity()).getSupportActionBar();
            action.setDisplayShowTitleEnabled(true);
            action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);
            action.setTitle("Ricerca...");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}
