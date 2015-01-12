package com.example.lisamazzini.train_app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;


public class JourneySerach extends ActionBarActivity {

    private TextView departure;
    private TextView arrival;
    private Button search;
    private Button favourites;
    JourneyFavouriteAdder journeyFavouriteAdder = JourneyFavouriteAdder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_serach);
        departure = (TextView)findViewById(R.id.tvInsertDeparture);
        arrival = (TextView)findViewById(R.id.tvInsertArrival);
        search = (Button)findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JourneySerach.this, MainActivity.class);
                intent.putExtra("journeyDeparture", departure.getText().toString());
                intent.putExtra("journeyArrival", arrival.getText().toString());
                startActivity(intent);
            }
        });

        journeyFavouriteAdder.setContext(JourneySerach.this);
        favourites = (Button)findViewById(R.id.btnFavourites);
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Set<String>> map = (Map<String, Set<String>>) journeyFavouriteAdder.getFavourites();
                Log.d("wowo", "map size " + map.size());
                for (String str : map.keySet()) {
                    for (String cazzi : map.get(str)) {
                        Log.d("wowo", "" + cazzi);
                    }
                }

                Intent i = new Intent(JourneySerach.this, FavouritesListActivity.class);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_journey_serach, menu);
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
