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

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class JourneyListActivity extends ActionBarActivity {

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
    ArrayList<List<Journey>> arList = new ArrayList<List<Journey>>(5);
    JourneyFavouriteAdder journeyFavouriteAdder = JourneyFavouriteAdder.getInstance();

    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);
        list = (ListView)findViewById(R.id.journey_list);
        PlannedJourneySearch journeySearch;
        simple = new JourneyAdapter(JourneyListActivity.this, jList);
        list.setAdapter(simple);
        try {
            this.selectTimeSlot();
        } catch (ParseException e) {
            e.printStackTrace();
        }

         for (int i = 0; i < N_TIME_SLOT; i++) {
             arList.add(new LinkedList<Journey>());
         }

        Intent intent = getIntent();
        departure = intent.getStringExtra("journeyDeparture");
        arrival = intent.getStringExtra("journeyArrival");

        journeyFavouriteAdder.setContext(JourneyListActivity.this);
        saveJourney = (Button)findViewById(R.id.btnAddToFavourites);
        saveJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] s = new String[2];
                s[0] = departure;
                s[1] = arrival;
                journeyFavouriteAdder.addFavourite(s);
                Toast.makeText(getApplicationContext(), "aggiunto tragitto " + s[0] + " - " + s[1], Toast.LENGTH_SHORT).show();
//                Map<String, Set<String>> map = (Map<String, Set<String>>) journeyFavouriteAdder.getFavourites();
//                for (String str : map.keySet()) {
//                    for (String cazzi : map.get(str)) {
//                        Log.d("wowo", "" + cazzi);
//                    }
//                    Log.d("wowo", "");
//                }
            }
        });


        radioButSelector = timeSlot;
        for (i = 0; i < N_TIME_SLOT; i++){
            journeySearch = new PlannedJourneySearch();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                journeySearch.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, radioButSelector);
            } else {
                journeySearch.execute(radioButSelector);
            }

            if (radioButSelector == N_TIME_SLOT) {
                radioButSelector = timeSlot-1;
            } else if (radioButSelector < timeSlot) {
                radioButSelector--;
            } else {
                radioButSelector++;
            }
        }
   }

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

    private class PlannedJourneySearch extends AsyncTask<Integer, Void, List<Journey>> {

        JsoupPlannedJourney journey = new JsoupPlannedJourney();

        int radioBut;

        protected void onPreExecute() {
        }

        @Override
        protected List<Journey> doInBackground(Integer... radio) {
            this.radioBut = radio[0];
            if (radioBut == timeSlot) {
                journey.computeResult(radio[0], timeSlot, departure, arrival);
            } else {
                journey.computeResult(radio[0], departure, arrival);
            }
            return journey.getJourneysList();
        }

        @Override
        protected void onPostExecute(List<Journey> result) {
            if (result.size() != 0) {


                arList.add(radioBut-1, result);
                jList.removeAll(jList);
                for (List<Journey> l : arList) {
                    jList.addAll(l);
                }
                if (result.get(0).isFirstNotArrived()) {
                    firstNotArrived = result.get(0);
                }
                firstNotArrivedPosition = jList.indexOf(firstNotArrived);
                Log.d("wowo", "" + firstNotArrivedPosition);
                simple.notifyDataSetChanged();
                list.post( new Runnable() {
                    @Override
                    public void run() {
                      Log.d("wowo", "" + firstNotArrived);
//                      list.smoothScrollToPosition(firstNotArrivedPosition);
                      list.setSelection(firstNotArrivedPosition);
//                      list.smoothScrollToPositionFromTop(firstNotArrivedPosition, 10);
                    }
                });


/*                if (radioBut >= timeSlot) {
                    numList.addAll(result);
                    for (Journey j : result) {
                        lista.add(j.getId() + " " + j.getDepartureTime());
                        System.out.println(j.toString() + "             " + radioBut);
                    }
                } else {
                    numList.addAll(0, result);
                    LinkedList<String> l = new LinkedList<>();
                    for (Journey j : result) {
                        l.add(j.getId() + " " + j.getDepartureTime());
                        System.out.println(j.toString() + "             " + radioBut);
                    }
                    lista.addAll(0, l);
                }*/
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
