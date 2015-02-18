package com.example.lisamazzini.train_app.GUI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.lisamazzini.train_app.GUI.Fragment.StationListFragment;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;

public class StationListActivity extends AbstractBaseActivity {

    private String trainNumber;
    private String stationCode;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        overridePendingTransition(R.transition.pull_in_right, R.transition.pull_out_left);

        getIntents();
        super.getToolbar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, StationListFragment.newInstance());
        StationListFragment fragment = (StationListFragment) fragmentManager.findFragmentById(R.id.stationListFragment);
        fragment.makeRequest(trainNumber, stationCode);
        Toast.makeText(this, "Ricerca in corso...", Toast.LENGTH_LONG).show();
    }

    @Override
    protected String setToolbarTitle() {
        return "Cerco...";
    }

    @Override
    protected void getIntents() {
        Intent i = getIntent();
        trainNumber = i.getStringExtra(Constants.TRAIN_N_EXTRA);
        stationCode = i.getStringExtra(Constants.ID_ORIGIN_EXTRA);
    }
}


