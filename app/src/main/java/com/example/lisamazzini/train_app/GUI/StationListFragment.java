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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.GUI.Adapter.StationListAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Fermate;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;

public class StationListFragment extends Fragment {

    private RecyclerView recyclerView;
    private StationListAdapter adapter;
    private LinearLayoutManager manager;
    private List<Fermate> fermateList = new LinkedList<>();


    private StationListController listController;
    private IFavouriteController favController = FavouriteTrainController.getInstance();
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

    private String[] trainDetails;
    private String trainNumber;
    private String stationCode;

    TextView info;
    TextView delay;
    TextView progress;
    TextView lastSeenTime;
    TextView lastSeenStation;
    private Menu menu;


    public static StationListFragment newInstance() {
        return new StationListFragment();
    }

    public StationListFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View layoutInflater = inflater.inflate(R.layout.fragment_station_list, container, false);
        this.recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.recycler);

        info = (TextView)layoutInflater.findViewById(R.id.tInfo);
        delay = (TextView)layoutInflater.findViewById(R.id.tDelay);
        progress = (TextView)layoutInflater.findViewById(R.id.tProgress);
        lastSeenTime = (TextView)layoutInflater.findViewById(R.id.tLastSeenTime);
        lastSeenStation = (TextView)layoutInflater.findViewById(R.id.tLastSeenStation);

        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new StationListAdapter(fermateList);
        adapter.notifyDataSetChanged();

        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());


        this.favController.setContext(getActivity().getApplicationContext());

        this.trainNumber = getActivity().getIntent().getStringExtra("trainNumber");
        this.stationCode = getActivity().getIntent().getStringExtra("stationCode");


        return layoutInflater;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        Log.d("cazzi", "fatto oncreate");
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;

    }

    private void setAsFavouriteIcon(boolean b) {
        if (menu == null) {
            Log.d("cazzi", "menu è null");
        }
        menu.getItem(1).setVisible(!b);
        menu.getItem(2).setVisible(b);
    }

    private void toggleFavouriteIcon() {
        if (favController.isFavourite(trainNumber, stationCode)) {
            setAsFavouriteIcon(true);
        } else {
            setAsFavouriteIcon(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            return true;
        }
        if (id == R.id.action_prefere) {
            try {
                addFavourite();
                setAsFavouriteIcon(true);
            } catch (FavouriteException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.action_deprefere) {
            removeFavourite();
            setAsFavouriteIcon(false);
        }
        return super.onOptionsItemSelected(item);
    }

    public void removeFavourite() {
        Log.d("cazzi", "rimuovo");
        favController.removeFavourite(trainNumber, stationCode);
    }

    public void addFavourite() throws FavouriteException {
        Log.d("cazzi", "aggiungo");
        favController.addFavourite(trainNumber, stationCode);
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
                    stationCode = trainDetails[1];
                    toggleFavouriteIcon();
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
                                    toggleFavouriteIcon();
                                    spiceManager.execute(listController.getNumberAndCodeRequest(), new AnotherListener());
                                    dialog.dismiss();
                                    break;
                                case 1:
                                    trainDetails = secondChoiceData;
                                    listController.setCode(secondChoiceData[1]);
                                    toggleFavouriteIcon();
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
            return getActivity();
        }
    }


    private class AnotherListener extends AbstractListener<NewTrain>{
        @Override
        public Context getContext() {
            return getActivity().getApplicationContext();
        }

        @Override
        public void onRequestSuccess(NewTrain trainResponse) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(trainResponse.getCategoria() + " " + trainResponse.getNumeroTreno());

            trainResponse.setProgress(listController.getProgress(trainResponse));

            fermateList.clear();
            fermateList.addAll(trainResponse.getFermate());
            adapter.notifyDataSetChanged();
//            bFavourite.setVisibility(View.VISIBLE);

            info.setText(trainResponse.getSubTitle());
            delay.setText(trainResponse.getRitardo().toString() + "'");
            progress.setText(trainResponse.getProgress());
            lastSeenTime.setText(trainResponse.getCompOraUltimoRilevamento());
            lastSeenStation.setText(trainResponse.getStazioneUltimoRilevamento());
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
