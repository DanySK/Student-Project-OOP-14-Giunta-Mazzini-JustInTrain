package com.example.lisamazzini.train_app.GUI.Activity;

import android.view.Menu;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.GUI.Fragment.JourneyResultsFragment;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;

public class JourneyResultsActivity extends AbstractBaseActivity {

    private JourneyResultsFragment fragment;
    private String departureStation;
    private String arrivalStation;
    private String requestedTime;
    private boolean isCustomTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        Intent i = getIntent();
        this.departureStation = i.getStringExtra("departureStation");
        this.arrivalStation = i.getStringExtra("arrivalStation");
        this.requestedTime = i.getStringExtra("requestedTime");
        this.isCustomTime = i.getBooleanExtra("isCustomTime", false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(departureStation + " " + arrivalStation);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        fragment = (JourneyResultsFragment) getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);
//        fragment.makeOuterRequestsWithStations(departureStation, arrivalStation, requestedTime);
        fragment.makeRequest(Constants.WITH_STATIONS, requestedTime, isCustomTime, departureStation, arrivalStation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            return true;
        }
        if (id == R.id.action_prefere) {
            return false;
        } else if (id == R.id.action_deprefere) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

}
