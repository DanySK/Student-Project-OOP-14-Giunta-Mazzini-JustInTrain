package com.example.lisamazzini.train_app.gui.activity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.lisamazzini.train_app.gui.fragment.JourneyListFragment;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.model.TextConstants;

/**
 * Classe che ospita il fragment per la visualizzazione di una lista di journey.
 *
 * @author albertogiunta
 */
public class JourneyListActivity extends AbstractBaseActivity {

    private String departureStation;
    private String arrivalStation;
    private String requestedTime;
    private boolean isCustomTime;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        getIntents();
        super.getToolbar();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new JourneyListFragment());
        final JourneyListFragment fragment = (JourneyListFragment) fragmentManager.findFragmentById(R.id.journeyResultsFragment);
        fragment.makeRequest(Constants.WITH_STATIONS, requestedTime, isCustomTime, departureStation, arrivalStation);
        Toast.makeText(this, TextConstants.TOAST_PENDING_RESEARCH, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected final String setToolbarTitle() {
        return TextConstants.TOOLBAR_PENDING_RESEARCH;
    }

    @Override
    protected final void getIntents() {
        final Intent i = getIntent();
        departureStation = i.getStringExtra(Constants.DEPARTURE_STAT_EXTRA);
        arrivalStation = i.getStringExtra(Constants.ARRIVAL_STAT_EXTRA);
        requestedTime = i.getStringExtra(Constants.REQUESTED_TIME_EXTRA);
        isCustomTime = i.getBooleanExtra(Constants.IS_CUSTOM_TIME_EXTRA, false);
    }
}
