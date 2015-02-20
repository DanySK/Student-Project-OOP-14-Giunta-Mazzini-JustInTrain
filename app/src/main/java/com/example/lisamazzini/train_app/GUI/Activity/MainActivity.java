package com.example.lisamazzini.train_app.gui.activity;

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
import com.example.lisamazzini.train_app.gui.Fragment.JourneyResultsFragment;
import com.example.lisamazzini.train_app.gui.Fragment.NavigationDrawerFragment;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Constants;

/**
 * Classe che ospita il fragment per la visualizzazione di una lista di journey, è la main activity,
 * e gestisce quindi anche la visualizzazione dei preferiti nella toolbar in forma di spinner, e ospita il
 * navigation drawer.
 */
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment navigationDrawerFragment;
    private JourneyResultsFragment fragment;
    private MainController controller;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        this.controller = new MainController(MainActivity.this);

        navigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        fragment = (JourneyResultsFragment) getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            fragment.setMenu(menu);
            restoreToolbar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (navigationDrawerFragment.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_deprefere) {
            controller.removeFavourite();
            restoreToolbar();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo che aggiorna la toolbar nel caso siano stati aggiunti o rimossi preferiti.
     * Viene chiamato alla creazione dell'activity.
     */
    public void restoreToolbar() {

        controller.refreshLists();
        final SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getSupportActionBar().getThemedContext(), android.R.layout.simple_spinner_dropdown_item, controller.getFavouriteStationNames());
        if (controller.isPresentAnyFavourite()) {
            final ActionBar action = getSupportActionBar();
            final ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {
                @Override
                public boolean onNavigationItemSelected(final int position, final long l) {
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
            fragment.resetGui();
        }
    }

    /**
     * Questa classe non prevede la gestione tradizionale del navigation drawer (in forma di sola lista di elementi),
     * per cui questo metodo non ha senso di essere implementato (non verrà neanche chiamato), nonostante ne sia necessaria la presenza
     * dovuta all'interfaccia implementata per poter disporre del navigation drawer.
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(final int position) { }
}