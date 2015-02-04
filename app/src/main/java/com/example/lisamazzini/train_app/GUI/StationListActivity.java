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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.Controller.TrainDataRequest;
import com.example.lisamazzini.train_app.Controller.TrainRequest;
import com.example.lisamazzini.train_app.GUI.Adapter.StationListAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Parser.Fermate;
import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.example.lisamazzini.train_app.Parser.RestClientTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.jsoup.Connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class StationListActivity extends Activity{

    private RecyclerView stationList;
    private Button bFavourite;
    private TextView tData;

    private StationListController listController;
    private FavouriteTrainController favController;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

    private String[] trainDetails;
    private String trainNumber;
    private String stationCode;

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
        this.stationCode = getIntent().getStringExtra("stationCode");
        this.listController = new StationListController(this.trainNumber);
        this.favController = new FavouriteTrainController(this);

        this.bFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favController.addFavourite(trainDetails[0] + Constants.SEPARATOR + trainDetails[1]);
                Toast.makeText(StationListActivity.this, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();
            }
        });

        this.bFavourite.setVisibility(View.INVISIBLE);

        if(this.stationCode == null) {
            spiceManager.execute(listController.getNumberRequest(), new TrainAndStationsRequestListener());
        }else{
            listController.setCode(this.stationCode);
            spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
        }
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


    private class TrainAndStationsRequestListener implements RequestListener<String> {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StationListActivity.this);

        //If there's no internet connection
        @Override
        public void onRequestFailure(final SpiceException spiceException) {
            dialogBuilder.setTitle("Problemi di connessione")
                    .setMessage("Controllare la propria connessione internet, patacca")
                    .setNeutralButton("Ok" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(StationListActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).show();

            Toast.makeText(StationListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


        //If there's internet connection I get the train details in this form  '608 - LECCE|608-S11145'
        @Override
        public void onRequestSuccess(final String data) {
            //The user put a not valid train number, the result is empty
            if(data == null){
                dialogBuilder.setTitle("Numero treno non valido!")
                    .setMessage("Il numero inserito non corrisponde a nessun cazzo di treno")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(StationListActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();
            //The train number is correct
            }else {
                //I used Constants.SEPARATOR to divide the result in case there are more train with the same number
                String[] datas = data.split(Constants.SEPARATOR);
                //Only one train
                if(datas.length == 1){
                    // I take the second part of the string, and divide it in 2; example 608 - S11145 -> [608,S11145]
                    datas = listController.computeData(datas[0]);
                    trainDetails = datas;
                    listController.setCode(datas[1]);
                    spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
                //There's more than one train with the same number
                }else{

                    //Here I take the data of the first train
                    final String[] firstChoiceData = listController.computeData(datas[0]);

                    //Here I take the data of the second train
                    final String[] secondChoiceData = listController.computeData(datas[1]);


                        //Here I create the options that will be showed to the user
                        String[] choices = listController.computeChoices(firstChoiceData, secondChoiceData);

                        dialogBuilder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:
                                        Log.d("Cosa", Arrays.toString(firstChoiceData));
                                        trainDetails = firstChoiceData;
                                        listController.setCode(firstChoiceData[1]);
                                        spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        Log.d("Cosa2", Arrays.toString(secondChoiceData));
                                        trainDetails = secondChoiceData;
                                        listController.setCode(secondChoiceData[1]);
                                        spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        }).show();
                }
            }
        }
    }

    private class AnotherListener implements RequestListener<NewTrain>{

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StationListActivity.this);
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            dialogBuilder.setTitle("Problemi di connessione")
                    .setMessage("Controllare la propria connessione internet, patacca")
                    .setNeutralButton("Ok" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(StationListActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).show();

            Toast.makeText(StationListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


        @Override
        public void onRequestSuccess(NewTrain trainResponse) {

                tData.setText(trainResponse.getCategoria() + " " + trainResponse.getNumeroTreno());
                stationList.setAdapter(new StationListAdapter(trainResponse.getFermate()));
                bFavourite.setVisibility(View.VISIBLE);

        }
    }


}


