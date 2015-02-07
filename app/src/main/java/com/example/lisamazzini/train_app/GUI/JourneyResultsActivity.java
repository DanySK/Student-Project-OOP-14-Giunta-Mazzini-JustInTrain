package com.example.lisamazzini.train_app.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.R;

public class JourneyResultsActivity extends ActionBarActivity {

    private JourneyResultsFragment fragment;
    private Toolbar toolbar;
    private String departureStation;
    private String arrivalStation;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        Intent i = getIntent();
        this.departureStation = i.getStringExtra("departureStation");
        this.arrivalStation = i.getStringExtra("arrivalStation");


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(departureStation + " " + arrivalStation);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        fragment = (JourneyResultsFragment)getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);

        fragment.makeRequestsWithStations(departureStation, arrivalStation);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
