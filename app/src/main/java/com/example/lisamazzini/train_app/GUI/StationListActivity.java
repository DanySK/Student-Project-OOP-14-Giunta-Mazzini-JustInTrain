package com.example.lisamazzini.train_app.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.R;

public class StationListActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private StationListFragment fragment;
    private String trainNumber;
    private String stationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        overridePendingTransition(R.transition.pull_in_right, R.transition.pull_out_left);

        Intent i = getIntent();
        this.trainNumber = i.getStringExtra("trainNumber");
        this.stationCode = i.getStringExtra("stationCode");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Searching...");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, StationListFragment.newInstance());
        fragment = (StationListFragment) fragmentManager.findFragmentById(R.id.stationListFragment);
        fragment.makeRequest(trainNumber, stationCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
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


