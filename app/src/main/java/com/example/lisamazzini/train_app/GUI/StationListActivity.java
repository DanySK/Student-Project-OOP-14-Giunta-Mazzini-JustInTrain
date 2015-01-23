package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Older.Train;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class StationListActivity extends Activity{

    private StationListController listController;
    private FavouriteTrainController favController;
    private final SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

    private String trainNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_results);
        //  TODO adapter, listview o quel che è + aggiunta ai preferiti
        this.trainNumber = getIntent().getStringExtra("trainNumber");
        this.listController = new StationListController(this.trainNumber);

        spiceManager.execute(listController.getRequest(), new TrainAndStationsRequestListener());
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


    private class TrainAndStationsRequestListener implements RequestListener<Train> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(StationListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
            //AGGIORNERA' LA VIEW
            //trainDetails.setText("Tipo: " + train.getCategory() + " Numero: " + train.getNumber() + "\nRitardo: " + train.getDelay());
            // list.setAdapter(new StationAdapter(StationListActivity.this, train.getStationList()));

        }
    }
}
