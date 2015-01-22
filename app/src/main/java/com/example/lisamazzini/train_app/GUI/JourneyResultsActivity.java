package com.example.lisamazzini.train_app.GUI;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.JourneyResultsController;
import com.example.lisamazzini.train_app.JourneyListwRobospiceActivity;
import com.example.lisamazzini.train_app.ListJourney;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 *
 */

public class JourneyResultsActivity extends ActionBarActivity {

    private final JourneyResultsController journeyController = new JourneyResultsController("departureString", "arrivalString");
    private final SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_results);
        //  TODO adapter, listview o quel che è,
    }

    private void makeRequests() {
        for (int i = 0; i < Constants.N_TIME_SLOT; i++) {
            spiceManager.execute(journeyController.iterateTimeSlots(), new JsoupPlannedJourneyRequestListener());
        }
    }

    private class JsoupPlannedJourneyRequestListener implements RequestListener<ListJourney> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(JourneyResultsActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(ListJourney journeys) {
            //  TODO prendi journeys e riempici la lista
        }
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_journey_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
