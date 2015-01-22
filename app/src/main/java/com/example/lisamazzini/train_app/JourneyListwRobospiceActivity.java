package com.example.lisamazzini.train_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.*;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class JourneyListwRobospiceActivity extends ActionBarActivity {

    private static final int N_TIME_SLOT = 5;
    JourneyAdapter simple;
    int timeSlot = -1;
    int radioButSelector = -1;
    int firstNotArrivedPosition = 0;
    Journey firstNotArrived = null;
    String departure;
    String arrival;

    ListView list;
    Button saveJourney;
    LinkedList<Journey> jList = new LinkedList<>();
    LinkedList<String> lista = new LinkedList<>();
    ArrayList<List<Journey>> arList = new ArrayList<>(5);
    JourneyFavouriteAdder journeyFavouriteAdder = JourneyFavouriteAdder.getInstance();
    private int lisa;

    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CAZZI", "CREATA ACTIVITY");
        setContentView(R.layout.activity_journey_list);
        list = (ListView)findViewById(R.id.journey_list);
        simple = new JourneyAdapter(JourneyListwRobospiceActivity.this, jList);
        list.setAdapter(simple);
        for (int i = 0; i < N_TIME_SLOT; i++) {
            arList.add(new LinkedList<Journey>());
        }

        /***/
        Intent intent = getIntent();
        departure = intent.getStringExtra("journeyDeparture");
        arrival = intent.getStringExtra("journeyArrival");
        /***/
        journeyFavouriteAdder.setContext(JourneyListwRobospiceActivity.this);
        saveJourney = (Button)findViewById(R.id.btnAddToFavourites);
        saveJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] s = new String[2];
                s[0] = departure;
                s[1] = arrival;
                journeyFavouriteAdder.addFavourite(s);
                Toast.makeText(getApplicationContext(), "aggiunto tragitto " + s[0] + " - " + s[1], Toast.LENGTH_SHORT).show();
            }
        });

        try {
            this.selectTimeSlot();
            radioButSelector = timeSlot;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        iterateTimeSlots();

    }

    private void iterateTimeSlots() {
        for (int i = 0; i < N_TIME_SLOT; i++) {
            Log.d("CAZZI", "lancio spiceManager " + radioButSelector);
            JsoupPlannedJourneyRequest request = new JsoupPlannedJourneyRequest(radioButSelector, new JsoupPlannedJourney(), this.timeSlot, this.departure, this.arrival);
            spiceManager.execute(request, new JsoupPlannedJourneyRequestListener());
            if (radioButSelector == N_TIME_SLOT) {
                radioButSelector = timeSlot - 1;
            } else if (radioButSelector < timeSlot) {
                radioButSelector--;
            } else {
                radioButSelector++;
            }
        }
    }

    /***/
    public class JsoupPlannedJourneyRequestListener implements RequestListener <ListJourney>{
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(JourneyListwRobospiceActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(ListJourney cazzi) {
            int index = cazzi.getRadio();
            List<Journey> result = cazzi.getList();
            if (result.size() != 0) {
                Log.d("CAZZI", "onRequestSuccess || radio " + index + " timeSlot " + timeSlot);

                arList.add(index-1, result);
                jList.removeAll(jList);
                for (List<Journey> l : arList) {
                    jList.addAll(l);
                }
                //controllo e metto la lista al primo non arrivato
                if (result.get(0).isFirstNotArrived()) {
                    firstNotArrived = result.get(0);
                }
                firstNotArrivedPosition = jList.indexOf(firstNotArrived);
                simple.notifyDataSetChanged();
                list.post(new Runnable() {
                    @Override
                    public void run() {
                        list.setSelection(firstNotArrivedPosition);
                    }
                });
            }
        }
    }
    /***/


    private void selectTimeSlot() throws ParseException {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date earlyNight = time.parse("00:00");
        Date earlyMorning = time.parse("06:00");
        Date morning = time.parse("13:00");
        Date afternoon = time.parse("18:00");
        Date evening = time.parse("22:00");
        Date lateNight = time.parse("23:59");
        Date actualTime = time.parse(time.format(Calendar.getInstance().getTime()));
        if (isBetween(new DateTime(earlyNight), new DateTime(earlyMorning), new DateTime(actualTime))) {
            this.timeSlot = 1;
        } else if (isBetween(new DateTime(earlyMorning), new DateTime(morning), new DateTime(actualTime))) {
            this.timeSlot = 2;
        } else if (isBetween(new DateTime(morning), new DateTime(afternoon), new DateTime(actualTime))) {
            this.timeSlot = 3;
        } else if (isBetween(new DateTime(afternoon), new DateTime(evening), new DateTime(actualTime))) {
            this.timeSlot = 4;
        } else if (isBetween(new DateTime(evening), new DateTime(lateNight), new DateTime(actualTime))) {
            this.timeSlot = 5;
        }
    }

    private boolean isBetween(DateTime initialTime, DateTime finalTime, DateTime actualTime) {
        return (Minutes.minutesBetween(initialTime, actualTime).getMinutes()) >= 0 &&
                (Minutes.minutesBetween(actualTime, finalTime).getMinutes()) > 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_journey_listw_robospice, menu);
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
