package com.example.lisamazzini.train_app.GUI;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Constants;


import java.util.List;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ArrayList<String> journeys;

    private SpinnerAdapter spinnerAdapter;
    private JourneyResultsFragment fragment;
    private IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();
    private ActionBar.OnNavigationListener navigationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        favouriteJourneyController.setContext(getApplicationContext());
        favouriteJourneyController.removeFavourites();
        favouriteJourneyController.addFavourite("pesaro", "7104", "cesena", "5066");
        favouriteJourneyController.addFavourite("pesaro", "7104", "lecce", "11145");

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
        journeys = new ArrayList<String>(favouriteJourneyController.getFavouritesAsList());
        final List<List<String>> finalJourneys = new ArrayList<List<String>>();
        for (int i = 0; i < 2; i++) {
            finalJourneys.add(new ArrayList<String>());
        }
        for (String s : journeys) {
            String[] splitted = s.split(Constants.SEPARATOR);
            finalJourneys.get(0).add(splitted[0] + " " + splitted[2]);
            finalJourneys.get(1).add(splitted[1] + Constants.SEPARATOR + splitted[3]);
        }
        spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, finalJourneys.get(0));

        ActionBar action = getSupportActionBar();
        navigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long l) {
                String[] IDs = finalJourneys.get(1).get(position).split(Constants.SEPARATOR);
                Log.d("cazzi", "faccio richieste con " + IDs[0] + " " + IDs[1]);
                fragment.makeRequestsWithIDs(IDs[0], IDs[1]);
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
}