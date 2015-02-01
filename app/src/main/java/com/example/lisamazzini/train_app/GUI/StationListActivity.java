package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.Exceptions.DoubleTrainNumberException;
import com.example.lisamazzini.train_app.Exceptions.FieldNotBuiltException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.GUI.Adapter.StationListAdapter;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Train;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.w3c.dom.Text;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class StationListActivity extends Activity{

    private StationListController listController;
    private FavouriteTrainController favController;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private RecyclerView stationList;
    private Button bFavourite;
    private TextView tData;


    private String trainNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        overridePendingTransition(R.transition.pull_in_right, R.transition.pull_out_left);

        this.bFavourite = (Button)findViewById(R.id.add_favourite);
        this.tData = (TextView)findViewById(R.id.train_details_text);

        this.stationList = (RecyclerView)findViewById(R.id.recycler);
        this.stationList.setLayoutManager(new LinearLayoutManager(this));
        this.stationList.setHasFixedSize(true);
        this.stationList.setItemAnimator(new DefaultItemAnimator());

        this.trainNumber = getIntent().getStringExtra("trainNumber");
        this.listController = new StationListController(this.trainNumber);
        this.favController = new FavouriteTrainController(this);

        this.bFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favController.addFavourite(trainNumber);
                Toast.makeText(StationListActivity.this, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();

            }
        });

        this.bFavourite.setVisibility(View.INVISIBLE);

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
            if(spiceException.getCause() instanceof DoubleTrainNumberException){
               // PopupMenu popupMenu = new PopupMenu(StationListActivity.this,  );


            } else if(spiceException.getCause() instanceof InvalidTrainNumberException){
                Log.d("BUHUUU", "sono triste-----------------------------------");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StationListActivity.this);
                dialogBuilder.setTitle("Numero treno non valido!")
                                .setMessage("Il numero inserito non corrisponde a nessun cazzo di treno")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(StationListActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();

            }
            Toast.makeText(StationListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
            tData.setText("Treno: " + train.getCategory() + train.getNumber() + "\nRitardo: " + train.getDelay());
            try {
                stationList.setAdapter(new StationListAdapter(train.getStationList()));
                bFavourite.setVisibility(View.VISIBLE);

            } catch (FieldNotBuiltException e) {
                e.printStackTrace();
            }
        }
    }
}
