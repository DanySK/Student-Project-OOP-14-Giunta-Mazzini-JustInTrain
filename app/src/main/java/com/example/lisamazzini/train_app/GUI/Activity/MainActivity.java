package com.example.lisamazzini.train_app.GUI.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.example.lisamazzini.train_app.Controller.MainController;
import com.example.lisamazzini.train_app.GUI.Fragment.JourneyResultsFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.NavigationDrawerFragment;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Constants;

import java.util.LinkedList;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment navigationDrawerFragment;
    private JourneyResultsFragment fragment;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        navigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        this.controller = new MainController(MainActivity.this);

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
            fragment.setMenu(menu);
            restoreToolbar();
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
        if (id == R.id.action_deprefere) {
            controller.removeFavourite();
            restoreToolbar();
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreToolbar() {

        controller.refreshLists();
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getSupportActionBar().getThemedContext(), android.R.layout.simple_spinner_dropdown_item, controller.getFavouriteStationNames());
        if (controller.isPresentAnyFavourite()) {
            ActionBar action = getSupportActionBar();
            ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(int position, long l) {
                    controller.setCurrentJourney(position);
                    fragment.setAsFavouriteIcon(true);
                    fragment.makeRequest(Constants.WITH_IDS, navigationDrawerFragment.getActualTime(), false, controller.getActualDepartureId(), controller.getActualArrivalId());
                    return true;
                }
            };
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            action.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
            action.setListNavigationCallbacks(spinnerAdapter, navigationListener);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setTitle("Nessuna tratta favorita!");
            fragment.resetGui(new LinkedList<PlainSolution>());
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    }
}