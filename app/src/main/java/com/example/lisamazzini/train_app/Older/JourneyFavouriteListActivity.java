package com.example.lisamazzini.train_app.Older;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class JourneyFavouriteListActivity extends ActionBarActivity {

    List<String> list = new LinkedList<>();
    JourneyFavouriteAdder adder = new JourneyFavouriteAdder();
    ArrayAdapter aAdpt;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_list);
        adder.setContext(JourneyFavouriteListActivity.this);
        lv = (ListView) findViewById(R.id.listView);
        aAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);

        registerForContextMenu(this.lv);

        Map<String, Set<String>> map = (Map<String, Set<String>>) adder.getFavourites();
        List<String> lstr = new LinkedList<>();
        for (String str : map.keySet()) {
            for (String s : map.get(str)) {
                lstr.add(s);
            }
            list.add(lstr.get(0) + "_" + lstr.get(1));
            lstr.removeAll(lstr);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                String[] ar = item.split("_");
                Log.d("wowo", ar[0] + " " + ar[1]);
                Intent i = new Intent(JourneyFavouriteListActivity.this, JourneyListwRobospiceActivity.class);
                i.putExtra("journeyDeparture", ar[0]);
                i.putExtra("journeyArrival", ar[1]);
                startActivity(i);
            }
        });

        lv.setAdapter(aAdpt);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fav_train, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) item.getMenuInfo();

        Journey prova = (Journey)lv.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.delete:
                adder.removeFavourite(prova.getDepartureStation());
                Toast.makeText(JourneyFavouriteListActivity.this, "Rimosso dai preferiti", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_favourites_list, menu);
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
