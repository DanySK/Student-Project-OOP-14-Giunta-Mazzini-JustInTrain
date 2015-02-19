package com.example.lisamazzini.train_app.GUI.Activity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.lisamazzini.train_app.GUI.Fragment.JourneyResultsFragment;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;

public class JourneyListActivity extends AbstractBaseActivity {

    private String departureStation;
    private String arrivalStation;
    private String requestedTime;
    private boolean isCustomTime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        getIntents();
        super.getToolbar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        JourneyResultsFragment fragment = (JourneyResultsFragment) fragmentManager.findFragmentById(R.id.journeyResultsFragment);
        fragment.makeRequest(Constants.WITH_STATIONS, requestedTime, isCustomTime, departureStation, arrivalStation);

        //TODO astrai
        Toast.makeText(this, "Ricerca in corso...", Toast.LENGTH_LONG).show();

    }

    @Override
    protected String setToolbarTitle() {

        //TODO astrai
        return "Cerco...";
    }

//    @Override
//    protected String setToolbarTitle() {
//        return (departureStation + " " + arrivalStation);
//    }

    @Override
    protected void getIntents() {
        Intent i = getIntent();
        departureStation = i.getStringExtra(Constants.DEPARTURE_STAT_EXTRA);
        arrivalStation = i.getStringExtra(Constants.ARRIVAL_STAT_EXTRA);
        requestedTime = i.getStringExtra(Constants.REQUESTED_TIME_EXTRA);
        isCustomTime = i.getBooleanExtra(Constants.IS_CUSTOM_TIME_EXTRA, false);
    }
}
