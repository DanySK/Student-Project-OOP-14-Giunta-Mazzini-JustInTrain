package com.example.lisamazzini.train_app.GUI.Activity;

import android.support.v7.app.ActionBarActivity;
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
import android.support.v7.widget.Toolbar;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.GUI.Fragment.JourneyResultsFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.NavigationDrawerFragment;
import com.example.lisamazzini.train_app.GUI.INavgationDrawerUtils;
import com.example.lisamazzini.train_app.GUI.NavigationDrawerUtils;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class MainActivity extends ActionBarActivity implements INavgationDrawerUtils {

    private NavigationDrawerFragment navigationDrawerFragment;
    private INavgationDrawerUtils navgationDrawerUtils;
    private JourneyResultsFragment fragment;
    private IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();

    private final List<String> favouriteStationNames = new LinkedList<>();
    private final List<String> favouriteStationIDs = new LinkedList<>();
    private final List<String> actualJourneyIDs = new ArrayList<>();

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        navgationDrawerUtils = new NavigationDrawerUtils(MainActivity.this);
        navigationDrawerFragment = navgationDrawerUtils.getNavigationDrawerFragment();
        favouriteJourneyController.setContext(getApplicationContext());

        navigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        fragment = (JourneyResultsFragment)getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            this.menu = menu;
            fragment.getFragmentUtils().setMenu(menu);
            restoreActionBar(menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (navigationDrawerFragment.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_prefere) {
            return true;
        } else if (id == R.id.action_deprefere) {
            favouriteJourneyController.removeFavourite(actualJourneyIDs.get(0).split(Constants.SEPARATOR)[0], actualJourneyIDs.get(0).split(Constants.SEPARATOR)[1]);
            restoreActionBar(menu);
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshLists() {
        favouriteStationIDs.clear();
        favouriteStationNames.clear();

        Map<String, String> favouriteJourneysMap;
        for (String s : (favouriteJourneysMap = (Map<String, String>) favouriteJourneyController.getFavouritesAsMap()).keySet()) {
            favouriteStationIDs.add(s);
        }

        for (String s : favouriteStationIDs) {
            favouriteStationNames.add(favouriteJourneysMap.get(s).replaceAll(Constants.SEPARATOR, " "));
        }
    }

    public void restoreActionBar(Menu menu) {
        final MenuItem notFavItem= menu.findItem(R.id.action_prefere);
        final MenuItem isFavItem = menu.findItem(R.id.action_deprefere);

        refreshLists();
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(getSupportActionBar().getThemedContext(), android.R.layout.simple_spinner_dropdown_item, favouriteStationNames);
        if (favouriteStationNames.size() > 0 ) {
            ActionBar action = getSupportActionBar();
            ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(int position, long l) {
                    actualJourneyIDs.clear();
                    actualJourneyIDs.add(favouriteStationIDs.get(position));
                    actualJourneyIDs.add(favouriteStationNames.get(position));
//                    isFavItem.setVisible(true);
//                    notFavItem.setVisible(false);
                    fragment.getFragmentUtils().setAsFavouriteIcon(true);
                    fragment.makeRequest(Constants.WITH_IDS, navigationDrawerFragment.getActualTime(), false, favouriteStationIDs.get(position).split(Constants.SEPARATOR)[0], favouriteStationIDs.get(position).split(Constants.SEPARATOR)[1]);
                    return true;
                }
            };
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
            action.setListNavigationCallbacks(spinnerAdapter, navigationListener);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);
//            fragment.getFragmentUtils().setAllEnabled(false);
            getSupportActionBar().setTitle("Nessuna tratta favorita!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public NavigationDrawerFragment getNavigationDrawerFragment() {
        return null;
    }

    @Override
    public void showTimePickerDialog(View v) {
        navgationDrawerUtils.showTimePickerDialog(v);
    }

    @Override
    public void showDatePickerDialog(View v) {
        navgationDrawerUtils.showDatePickerDialog(v);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        navgationDrawerUtils.onTimeSet(view, hourOfDay, minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        navgationDrawerUtils.onDateSet(view, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        navgationDrawerUtils.onNavigationDrawerItemSelected(position);
    }
}