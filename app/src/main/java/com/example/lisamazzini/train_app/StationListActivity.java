package com.example.lisamazzini.train_app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public class StationListActivity extends ActionBarActivity {

    private ListView list;
    private TextView trainDetails;
    private String trainNumber;
    private Train train;
    private Button fav;
    private TextView prova;
    private TrainFavouriteAdder favAdder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        //Initialize all the views
        this.list = (ListView)findViewById(R.id.listView);
        this.trainDetails = (TextView)findViewById(R.id.details);
        this.trainNumber = getIntent().getStringExtra("trainNumber");
        this.fav = (Button)findViewById(R.id.favourite);
        this.prova = (TextView)findViewById(R.id.prova);
        //Get an instance of the TrainFavouriteAdder
        favAdder = TrainFavouriteAdder.getInstance();
        favAdder.setContext(StationListActivity.this);

        //When the user click on the "Preferito" button, the train is added to the favourite
        this.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favAdder.addFavourite(trainNumber);
                Toast.makeText(StationListActivity.this, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();

            }
        });

        //Start the AsyncTask
        ScrapingTask connection = new ScrapingTask(this.trainNumber);
        connection.execute();



    }



    private class ScrapingTask extends AsyncTask<Void, Void, Train> {

        private String number;
        private final JsoupTrainDetails scraperTrain;
        private final JsoupJourneyDetails scraperJourney;
        private String progress;

        protected ScrapingTask(String trainNumber){
            this.number = trainNumber;
            this.scraperTrain = new JsoupTrainDetails(this.number);
            this.scraperJourney = new JsoupJourneyDetails(this.number);
        }

        @Override
        protected Train doInBackground(Void... params) {
            try {
                train = scraperTrain.computeResult();
                try {
                    scraperJourney.computeResult();
                    train = new Train(train, scraperJourney.getStationList());
                    progress = scraperJourney.getProgress();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
            }
            return train;
        }

        @Override
        protected void onPostExecute(Train train){
            trainDetails.setText("Tipo: " + train.getCategory() + " Numero: " + train.getNumber() + "\nRitardo: " + train.getDelay() + "\nAndamento: " + progress);
            list.setAdapter(new StationAdapter(StationListActivity.this, train.getStationList()));
        }
    }
}
