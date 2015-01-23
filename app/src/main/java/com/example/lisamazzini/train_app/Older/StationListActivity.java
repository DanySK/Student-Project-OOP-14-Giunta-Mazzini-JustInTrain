package com.example.lisamazzini.train_app.Older;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


public class StationListActivity extends ActionBarActivity {

    private ListView list;
    private TextView trainDetails;
    private String trainNumber;
    private Train train;
    private Button fav;
    private TextView prova;
    private TrainFavouriteAdder favAdder;
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
        setContentView(R.layout.activity_station_list);
        //Initialize all the views
        this.list = (ListView) findViewById(R.id.listView);
        this.trainDetails = (TextView) findViewById(R.id.details);
        this.trainNumber = getIntent().getStringExtra("trainNumber");
        this.fav = (Button) findViewById(R.id.favourite);
        this.prova = (TextView) findViewById(R.id.prova);
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
        spiceManager = new SpiceManager(UncachedSpiceService.class);
        TrainAndStationsRequest request = new TrainAndStationsRequest(this.trainNumber);
        spiceManager.execute(request, new TrainAndStationsRequestListener());
    }


    private class TrainAndStationsRequestListener implements RequestListener<Train> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(StationListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
            trainDetails.setText("Tipo: " + train.getCategory() + " Numero: " + train.getNumber() + "\nRitardo: " + train.getDelay());
            list.setAdapter(new StationAdapter(StationListActivity.this, train.getStationList()));

        }
    }


}