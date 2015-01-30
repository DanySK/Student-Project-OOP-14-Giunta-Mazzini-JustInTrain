package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.JourneyListWrapper;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.JourneyTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */

public class JourneyResultsActivity extends Activity {

    private JourneyResultsController journeyController;
    private final SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

     private JourneyTrain train = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private JourneyResultsAdapter journeyResultsAdapter;
    List<JourneyTrain> flatJourneyTrainsList = new LinkedList<>();
    private List<List<JourneyTrain>> journeyTrains = new ArrayList<>(Constants.N_TIME_SLOT);

    private String departure;
    private String arrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_results);

        Intent intent = getIntent();
        departure = intent.getStringExtra("journeyDeparture");
        arrival = intent.getStringExtra("journeyArrival");

        journeyController = new JourneyResultsController(departure, arrival);

        for (int i = 0; i < 5; i++) {
            journeyTrains.add(new LinkedList<JourneyTrain>());
        }

        recyclerView = (RecyclerView)findViewById(R.id.cardList);
        this.journeyResultsAdapter = new JourneyResultsAdapter(this.flatJourneyTrainsList);
        this.manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(journeyResultsAdapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //  TODO listener per salvare i preferiti con favouriteController
        makeRequests();
    }

    private void makeRequests() {
        for (int i = 0; i < Constants.N_TIME_SLOT; i++) {
            spiceManager.execute(journeyController.iterateTimeSlots(), new JourneyResultsRequestListener());
        }
    }

    private class JourneyResultsRequestListener implements RequestListener<JourneyListWrapper> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(JourneyResultsActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(final JourneyListWrapper journeys) {
            if (journeyController.newDataIsPresent(journeys.getList())) {
                flatJourneyTrainsList = journeyController.refillJourneyList(flatJourneyTrainsList, journeys.getList(), journeys.getTimeSlot());
                int pos = manager.findFirstCompletelyVisibleItemPosition();
                journeyResultsAdapter.notifyDataSetChanged();

                train = journeyController.getFirstTakeableTrain(journeys);

                if (train != null) {
//                // TODO fai che quando è nel tuo stesso timeslot controlli ogni treno con il primo buono da prendere e si posizioni su quello
                    manager.scrollToPositionWithOffset(flatJourneyTrainsList.indexOf(train), 0);
                    train = null;
                } else {
                    if (journeyController.newDataisInsertedBefore(journeys)) {
                        manager.scrollToPositionWithOffset(pos + journeys.getList().size(), 0);
                    } else {
                        manager.scrollToPositionWithOffset(pos, 0);
                    }
                }
            }
        }
    }

    // TODO fai che quando esci dall'activity ferma robospice e quando riprende riprende pure lui

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
