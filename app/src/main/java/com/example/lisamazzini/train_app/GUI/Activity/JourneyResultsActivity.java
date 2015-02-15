package com.example.lisamazzini.train_app.GUI.Activity;

import android.view.Menu;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lisamazzini.train_app.GUI.Fragment.JourneyResultsFragment;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;

public class JourneyResultsActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        Intent i = getIntent();
        String departureStation = i.getStringExtra(Constants.DEPARTURE_STAT_EXTRA);
        String arrivalStation = i.getStringExtra(Constants.ARRIVAL_STAT_EXTRA);
        String requestedTime = i.getStringExtra(Constants.REQUESTED_TIME_EXTRA);
        boolean isCustomTime = i.getBooleanExtra(Constants.IS_CUSTOM_TIME_EXTRA, false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(departureStation + " " + arrivalStation);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        JourneyResultsFragment fragment = (JourneyResultsFragment) getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);
        fragment.makeRequest(Constants.WITH_STATIONS, requestedTime, isCustomTime, departureStation, arrivalStation);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.getItem(0).setVisible(false);
//        menu.getItem(1).setVisible(false);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.home) {
//            return true;
//        }
//        if (id == R.id.action_prefere) {
//            return false;
//        } else if (id == R.id.action_deprefere) {
//            return false;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
