package com.example.lisamazzini.train_app.Older;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TrainFavouriteListActivity extends ActionBarActivity {

    private ListView list;
    private List<Train> favTrain;
    private TextView title;
    private Train train;
    private TrainFavouriteAdder fava;
    private TrainAdapter adapter;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);


    // definisci questi metodi
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
        setContentView(R.layout.activity_favourite_list);

        this.list = (ListView) findViewById(R.id.favList);
        this.title = (TextView) findViewById(R.id.textView2);
        this.favTrain = new LinkedList<>();

        //Get an instance of the TrainFavouriteAdder and set the context
        fava = TrainFavouriteAdder.getInstance();
        fava.setContext(TrainFavouriteListActivity.this);

        //Register a ContextMenu for this list, so that if the user perform a long click on a item, the onCreateContextMenu method will be called
        registerForContextMenu(this.list);

        //Get all the favourite trains saved in a map
        Map<String, String> map = (Map<String, String>) fava.getFavourites();

        //For each train start a AsyncTask to get the details

        for (String s : map.values()) {
            Log.d("--------------------------" , "sono qui nell'on create");
            TrainRequest request = new TrainRequest(s);
            spiceManager.execute(request, new TrainRequestListener());
        }

        //When the user click on one item of the list, it starts a new StationListActivity with the
        //information about that train
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Take the train number from the view
                TextView tv = (TextView) view.findViewById(R.id.trainNum);
                Intent intent = new Intent(TrainFavouriteListActivity.this, StationListActivity.class);
                intent.putExtra("trainNumber", tv.getText().toString());

                //Start the new activity
                startActivity(intent);
            }
        });




    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fav_train, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Train prova = (Train)list.getItemAtPosition(info.position);


        Intent intent = new Intent(TrainFavouriteListActivity.this, NotificationService.class);

       switch (item.getItemId()) {
            case R.id.delete:
                fava.removeFavourite(prova.getNumber());
                Toast.makeText(TrainFavouriteListActivity.this, "Rimosso dai preferiti", Toast.LENGTH_LONG).show();
                return true;
            case R.id.pin:
                Log.d("MA DIO BONO ------------------ ", "" + prova.getNumber());
                intent.putExtra("number", prova.getNumber());
                startService(intent);
                return true;
           case R.id.unpin:
                stopService(intent);
           default:
                return super.onContextItemSelected(item);
        }
    }

    private class TrainRequestListener implements RequestListener<Train> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(TrainFavouriteListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
            Log.d("--------------------------", "sono qui nell'on request success");
            favTrain.add(train);
            adapter = new TrainAdapter(TrainFavouriteListActivity.this, favTrain);
            list.setAdapter(adapter);

        }
    }
}