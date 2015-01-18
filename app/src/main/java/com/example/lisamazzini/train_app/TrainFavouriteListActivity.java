package com.example.lisamazzini.train_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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
            ScrapingTask scrapingTask = new ScrapingTask(s);
            scrapingTask.execute();
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

        Pinner pinner = new Pinner(this);

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
                //pinner.pinTrain(prova.getNumber());
                return true;
           case R.id.unpin:
                stopService(intent);
           default:
                return super.onContextItemSelected(item);
        }
    }


    private class ScrapingTask extends AsyncTask<Void, Void, Train> {

        //This class has a scraper for the train details
        private final JsoupTrainDetails scraperTrain;
         protected ScrapingTask(String trainNumber){
            this.scraperTrain = new JsoupTrainDetails(trainNumber);
        }

        @Override
        protected Train doInBackground(Void... params) {
            try {
                train = scraperTrain.computeResult();
            } catch (IOException e) {
            }
            return train;
        }

        @Override
        protected void onPostExecute(Train train){
            //After the execution of the task, the ListView is updated with the result.
            favTrain.add(train);
            adapter = new TrainAdapter(TrainFavouriteListActivity.this, favTrain);
            list.setAdapter(adapter);
        }
    }
}
