package com.example.lisamazzini.train_app.GUI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lisamazzini.train_app.GUI.Fragment.StationListFragment;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;

public class StationListActivity extends AbstractBaseActivity {

    private StationListFragment fragment;
    private String trainNumber;
    private String stationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        overridePendingTransition(R.transition.pull_in_right, R.transition.pull_out_left);

        Intent i = getIntent();
        this.trainNumber = i.getStringExtra(Constants.TRAIN_N_EXTRA);
        this.stationCode = i.getStringExtra(Constants.ID_ORIGIN_EXTRA);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Searching...");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, StationListFragment.newInstance());
        fragment = (StationListFragment) fragmentManager.findFragmentById(R.id.stationListFragment);
        Toast.makeText(this, "Ricerca in corso...", Toast.LENGTH_LONG).show();

        fragment.makeRequest(trainNumber, stationCode);
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
        if (id == android.R.id.home) {
            onBackPressed();
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


