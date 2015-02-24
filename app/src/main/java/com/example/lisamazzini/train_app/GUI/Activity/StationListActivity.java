package com.example.lisamazzini.train_app.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.lisamazzini.train_app.gui.fragment.StationListFragment;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.R;

/**
 * Classe che ospita il fragment per la visualizzazione di una lista di stazioni.
 *
 * @author lisamazzini
 */
public class StationListActivity extends AbstractBaseActivity {

    private String trainNumber;
    private String stationCode;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        overridePendingTransition(R.transition.pull_in_right, R.transition.pull_out_left);

        getIntents();
        super.getToolbar();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new StationListFragment());
        final StationListFragment fragment = (StationListFragment) fragmentManager.findFragmentById(R.id.stationListFragment);
        fragment.makeRequest(trainNumber, stationCode);
        Toast.makeText(this, Constants.TOAST_PENDING_RESEARCH, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected final String setToolbarTitle() {
        return Constants.TOOLBAR_PENDING_RESEARCH;
    }

    @Override
    protected final void getIntents() {
        final Intent i = getIntent();
        trainNumber = i.getStringExtra(Constants.TRAIN_N_EXTRA);
        stationCode = i.getStringExtra(Constants.ID_ORIGIN_EXTRA);
    }
}


