package com.example.lisamazzini.train_app.gui.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.gui.activity.JourneyListActivity;
import com.example.lisamazzini.train_app.gui.activity.StationListActivity;
import com.example.lisamazzini.train_app.gui.adapter.DrawerListAdapter;
import com.example.lisamazzini.train_app.gui.fragment.pickers.DatePickerFragment;
import com.example.lisamazzini.train_app.gui.fragment.pickers.TimePickerFragment;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Fragment che permette di visualizzare e interagire con il navigation drawer.
 *
 * @author albertogiunta
 */
public class NavigationDrawerFragment extends Fragment implements IBaseFragment {

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
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;



    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;


    private final Calendar calendar = Calendar.getInstance();
    private final Format formatter = new SimpleDateFormat(Constants.SDF_NO_SECS, Locale.ITALY);
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private String actualTime;
    private boolean isCustomTime;


    private Button timePickerButton;
    private Button datePickerButton;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public final void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {

        final View drawerView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        final EditText trainNumber = (EditText) drawerView.findViewById(R.id.eTrainNumber);
        final ImageButton trainNumberSearchButton = (ImageButton) drawerView.findViewById(R.id.bTrainNumberSearch);
        final EditText departure = (EditText) drawerView.findViewById(R.id.eDepartureStation);
        final EditText arrival = (EditText) drawerView.findViewById(R.id.eArrivalStation);
        timePickerButton = (Button) drawerView.findViewById(R.id.bTimePicker);
        datePickerButton = (Button) drawerView.findViewById(R.id.bDatePicker);
        final ImageButton journeySearchButton = (ImageButton) drawerView.findViewById(R.id.bJourneySearch);


        trainNumberSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (trainNumber.length() > Constants.EMPTY) {
                    final Intent i = new Intent(getActivity(), StationListActivity.class);
                    i.putExtra(Constants.TRAIN_N_EXTRA, Utilities.trimAndCapitalizeString(trainNumber.getText().toString()));
                    startActivity(i);
                }
            }
        });


        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showTimePickerDialog(v);
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDatePickerDialog(v);
            }
        });

        setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        // TODO se sono le 00:20 per esempio lui va alle 23:20 ma dello stesso giorno (dovrebbe essere di quello prima)
        setTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        actualTime = buildDateTime();

        journeySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (departure.length() > 0 && arrival.length() > 0) {
                    final Intent i = new Intent(getActivity(), JourneyListActivity.class);
                    i.putExtra(Constants.DEPARTURE_STAT_EXTRA, Utilities.trimAndCapitalizeString(departure.getText().toString()));
                    i.putExtra(Constants.ARRIVAL_STAT_EXTRA, Utilities.trimAndCapitalizeString(arrival.getText().toString()));
                    i.putExtra(Constants.REQUESTED_TIME_EXTRA, buildDateTime());
                    i.putExtra(Constants.IS_CUSTOM_TIME_EXTRA, isCustomTime);
                    startActivity(i);
                }
            }
        });

        final String[] titles = {"Treni preferiti", "Rimuovi treni preferiti", "Achievement"};

        final RecyclerView recyclerView = (RecyclerView) drawerView.findViewById(R.id.lDrawerList);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final DrawerListAdapter drawerListAdapter = new DrawerListAdapter(titles);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(drawerListAdapter);

        return drawerView;
    }

    private final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(final TimePicker view, final int pHourOfDay, final int pMinute) {
            if (view.isShown()) {
                setTime(pHourOfDay, pMinute, true);
            }
        }
    };

    private final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(final DatePicker view, final int pYear, final int pMonthOfYear, final int pDayOfMonth) {
            if (view.isShown()) {
                setDate(pYear, pMonthOfYear, pDayOfMonth, true);
            }
        }
    };
    ///////////////////////////////////////////////////////////////////////////////////////

    private void showTimePickerDialog(final View v) {
        final TimePickerFragment timeFragment = new TimePickerFragment();
        final Calendar calender = Calendar.getInstance();
        final Bundle args = new Bundle();
        args.putInt("hour", calender.get(Calendar.HOUR_OF_DAY));
        args.putInt("minute", calender.get(Calendar.MINUTE));
        timeFragment.setArguments(args);
        timeFragment.setCallback(timeListener);
        timeFragment.show(getFragmentManager(), "Time Picker");
    }

    private void showDatePickerDialog(final View v) {
        final DatePickerFragment dateFragment = new DatePickerFragment();
        final Calendar calender = Calendar.getInstance();
        final Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        dateFragment.setArguments(args);
        dateFragment.setCallback(dateListener);
        dateFragment.show(getFragmentManager(), "Date Picker");
    }

    private void setTime(final int pHour, final int pMinute, final boolean pIsCustomTime) {
        this.hour = pHour;
        this.minute = pMinute;
        this.isCustomTime = pIsCustomTime;
        timePickerButton.setText(String.format("%02d:%02d", pHour, pMinute));
    }

    private void setDate(final int pYear, final int pMonth, final int pDay, final boolean pIsCustomTime) {
        this.year = pYear;
        this.day = pDay;
        this.month = pMonth;
        this.isCustomTime = pIsCustomTime;
        datePickerButton.setText(String.format("%02d/%02d/%02d", pDay, pMonth, pYear));
    }

    private String buildDateTime() {
        calendar.set(this.year, this.month, this.day, this.hour, this.minute);
        return formatter.format(calendar.getTime());
    }

    /**
     * Metodo invocato per sapere se il drawer sia aperto o meno al momento.
     * @return boolean
     */
    public final boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }


    /**
     * Metodo invocato per settare il navigation drawer alla sua creazione (inflate e metodi di utility).
     * @param fragmentId fragmentId
     * @param drawerLayout drawerLayout
     */
    public final void setUp(final int fragmentId, final DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    final SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public final void onAttach(final Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public final void onDetach() {
        super.onDetach();
    }

    @Override
    public final void onSaveInstanceState(final Bundle outState) {
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public final void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public final void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.menu_main, menu);
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            final ActionBar action = ((ActionBarActivity) getActivity()).getSupportActionBar();
            action.setDisplayShowTitleEnabled(true);
            action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);
            action.setTitle("Ricerca...");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Getter per la stringa con l'ora selezionata.
     * @return la stringa con l'ora selezionata
     */
    public final String getActualTime() {
        return this.actualTime;
    }
}