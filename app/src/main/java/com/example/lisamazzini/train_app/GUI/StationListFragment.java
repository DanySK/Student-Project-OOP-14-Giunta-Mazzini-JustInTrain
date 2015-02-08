package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.GUI.Adapter.StationListAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Fermate;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;

public class StationListFragment extends Fragment{

    private RecyclerView recyclerView;
//    private Button bFavourite;
//    private TextView tData;
//    private TextView info;
    private StationListAdapter adapter;
    private LinearLayoutManager manager;
    private List<Fermate> fermateList = new LinkedList<>();


    private StationListController listController;
    private IFavouriteController favController = FavouriteTrainController.getInstance();
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

    private String[] trainDetails;
    private String trainNumber;
    private String stationCode;


    public static StationListFragment newInstance() {
        return new StationListFragment();
    }

    public StationListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View layoutInflater = inflater.inflate(R.layout.fragment_station_list, container, false);
        this.recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.recycler);

        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new StationListAdapter(fermateList);
        adapter.notifyDataSetChanged();

        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());


        this.favController.setContext(getActivity().getApplicationContext());

//        this.bFavourite = (Button)findViewById(R.id.add_favourite);
//        this.tData = (TextView)findViewById(R.id.train_details_text);
//        this.info = (TextView)findViewById(R.id.info);
//        this.trainNumber = getIntent().getStringExtra("trainNumber");
//        this.stationCode = getIntent().getStringExtra("stationCode");

//        this.bFavourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                favController.addFavourite(trainDetails[0], trainDetails[1]);
//                try {
//                    favController.addFavourite(trainDetails[0], trainDetails[1]);
//                    Toast.makeText(StationListActivity.this, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();
//                } catch (FavouriteException e) {
//                    Toast.makeText(StationListActivity.this, "E' già fra i preferiti!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        this.bFavourite.setVisibility(View.INVISIBLE);

        return layoutInflater;
    }

    public void makeRequest(String trainNumber, String stationCode) {
        this.trainNumber = trainNumber;
        this.stationCode = stationCode;
        this.listController = new StationListController(this.trainNumber);
        if(this.stationCode == null) {
            makeRequestWithNumber();
        }else{
            makeRequestWithNumberAndCode();
        }
    }


    private void makeRequestWithNumber() {
        spiceManager.execute(listController.getNumberRequest(), new TrainAndStationsRequestListener());
    }

    private void makeRequestWithNumberAndCode() {
        listController.setCode(this.stationCode);
        spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
    }

    private class TrainAndStationsRequestListener extends AbstractListener<String> {
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
                                Intent intent = new Intent(getContext(), MainActivity.class);
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
                                    trainDetails = firstChoiceData;
                                    listController.setCode(firstChoiceData[1]);
                                    spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
                                    dialog.dismiss();
                                    break;
                                case 1:
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

        @Override
        public Context getContext() {
            return getActivity().getApplicationContext();
        }
    }


    private class AnotherListener extends AbstractListener<NewTrain>{
        @Override
        public Context getContext() {
            return getActivity().getApplicationContext();
        }

        @Override
        public void onRequestSuccess(NewTrain trainResponse) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(trainResponse.getCategoria() + " " + trainResponse.getNumeroTreno() + " " + listController.getProgress(trainResponse));
            fermateList.clear();
            fermateList.addAll(trainResponse.getFermate());
            adapter.notifyDataSetChanged();
//            bFavourite.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }
}
