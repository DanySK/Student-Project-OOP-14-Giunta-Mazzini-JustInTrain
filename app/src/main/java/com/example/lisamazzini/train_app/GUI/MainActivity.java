package com.example.lisamazzini.train_app.GUI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SpinnerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Constants;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

//public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;

    private ArrayList<String> journeys;

    Map<String, String> map;
    final List<String> stationNames = new LinkedList<>();
    final List<String> IDs = new LinkedList<>();

    private Menu menu;
    private final List<String> actualJourneyIDs = new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;
    private JourneyResultsFragment fragment;
    private IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();
    private ActionBar.OnNavigationListener navigationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        favouriteJourneyController.setContext(getApplicationContext());
//        favouriteJourneyController.removeFavourites();
//        try {
//            favouriteJourneyController.addFavourite("07104", "05066", "pesaro", "cesena");
//            favouriteJourneyController.addFavourite("07104", "011145", "pesaro", "lecce");
//        } catch (FavouriteException e) {
//            e.printStackTrace();
//        }


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        fragment = (JourneyResultsFragment)getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);
    }

    @Override
         public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            this.menu = menu;
            restoreActionBar(menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_prefere) {
            try {
                favouriteJourneyController.addFavourite(actualJourneyIDs.get(0), actualJourneyIDs.get(1), actualJourneyIDs.get(2), actualJourneyIDs.get(3));
                item.setVisible(false);
                menu.getItem(2).setVisible(true);
                restoreActionBar(menu);
                Log.d("cazzi", "ho toccato un non preferito");
            } catch (FavouriteException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.action_deprefere) {
            favouriteJourneyController.removeFavourite(actualJourneyIDs.get(0).split(Constants.SEPARATOR)[0], actualJourneyIDs.get(0).split(Constants.SEPARATOR)[1]);
            Log.d("cazzi", "rimuovo " + actualJourneyIDs.get(0).split(Constants.SEPARATOR)[0] + " " + actualJourneyIDs.get(0).split(Constants.SEPARATOR)[1]);
            item.setVisible(false);
            menu.getItem(1).setVisible(true);
            Log.d("cazzi", "ho toccato un preferito");
            restoreActionBar(menu);
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshLists() {
        IDs.clear();
        stationNames.clear();
        for (String s : (map = (Map<String, String>) favouriteJourneyController.getFavouritesAsMap()).keySet()) {
            IDs.add(s);
        }

        for (String s : IDs) {
            stationNames.add(map.get(s).replaceAll(Constants.SEPARATOR, " "));
        }

    }

    public void restoreActionBar(Menu menu) {

        final MenuItem notFavItem= menu.findItem(R.id.action_prefere);
        final MenuItem isFavItem = menu.findItem(R.id.action_deprefere);

        refreshLists();

        spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, stationNames);
        if (stationNames.size() > 0 ) {
            ActionBar action = getSupportActionBar();
            navigationListener = new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(int position, long l) {
                    //                String[] IDs = finalJourneys.get(1).get(position).split(Constants.SEPARATOR);
                    //                Log.d("cazzi", "faccio richieste con " + IDs[0] + " " + IDs[1]);
                    actualJourneyIDs.clear();
                    actualJourneyIDs.add(IDs.get(position));
                    actualJourneyIDs.add(stationNames.get(position));
                    Log.d("cazzi", Arrays.toString(actualJourneyIDs.toArray()));
                    fragment.makeOuterRequestsWithIDs(IDs.get(position).split(Constants.SEPARATOR)[0], IDs.get(position).split(Constants.SEPARATOR)[1], mNavigationDrawerFragment.getActualTime());
                    isFavItem.setVisible(true);
                    notFavItem.setVisible(false);
                    return true;
                }
            };
            action.setDisplayShowTitleEnabled(false);
            action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
            action.setListNavigationCallbacks(spinnerAdapter, navigationListener);
        } else {
            isFavItem.setVisible(false);
            notFavItem.setVisible(false);
            getSupportActionBar().setTitle("Nessuna tratta favorita!");
        }

    }

    private TimePickerFragment timeFragment;
    private DialogFragment dateFragment;


    public void showTimePickerDialog(View v) {
        timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
            mNavigationDrawerFragment.setTime(hourOfDay, minute);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.isShown()) {
            mNavigationDrawerFragment.setDate(year, monthOfYear, dayOfMonth);
        }
    }
}