package com.example.lisamazzini.train_app.GUI.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.Model.Treno.Fermate;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;

public class StationListFragment extends AbstractRobospiceFragment {

    private RecyclerView recyclerView;
    private StationListAdapter adapter;
    private LinearLayoutManager manager;
    private List<Fermate> fermateList = new LinkedList<>();

    private StationListController listController;
    private IFavouriteController favController = FavouriteTrainController.getInstance();
    private FavouriteFragmentsUtils favouriteFragmentsUtils;


    private String[] trainDetails;
    private String trainNumber;
    private String stationCode;

    private TextView info;
    private TextView delay;
    private TextView progress;
    private TextView lastSeenTime;
    private TextView lastSeenStation;
    private TextView textDelay;
    private TextView textProgress;
    private TextView textLastSeen;

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
        super.spiceManager = new SpiceManager(UncachedSpiceService.class);
        View layoutInflater = inflater.inflate(R.layout.fragment_station_list, container, false);
        this.recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.recycler);

        info = (TextView)layoutInflater.findViewById(R.id.tInfo);
        delay = (TextView)layoutInflater.findViewById(R.id.tDelay);
        progress = (TextView)layoutInflater.findViewById(R.id.tProgress);
        lastSeenTime = (TextView)layoutInflater.findViewById(R.id.tLastSeenTime);
        lastSeenStation = (TextView)layoutInflater.findViewById(R.id.tLastSeenStation);
        textDelay = (TextView)layoutInflater.findViewById(R.id.lDelay);
        textLastSeen = (TextView)layoutInflater.findViewById(R.id.lLastSeen);
        textProgress = (TextView)layoutInflater.findViewById(R.id.lProgress);
        textDelay.setVisibility(View.INVISIBLE);
        textLastSeen.setVisibility(View.INVISIBLE);
        textProgress.setVisibility(View.INVISIBLE);

        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new StationListAdapter(fermateList);
        adapter.notifyDataSetChanged();

        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.favouriteFragmentsUtils = new FavouriteFragmentsUtils(FavouriteTrainController.getInstance());
        this.favController.setContext(getActivity().getApplicationContext());

        return layoutInflater;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        favouriteFragmentsUtils.setMenu(this.menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menu = favouriteFragmentsUtils.onOptionsItemSelected(item, trainDetails);
        return super.onOptionsItemSelected(item);
    }

    public void makeRequest(String trainNumber, String stationCode) {
        this.trainNumber = trainNumber;
        this.stationCode = stationCode;
        this.listController = new StationListController(this.trainNumber);
        if(this.stationCode == null) {
            spiceManager.execute(listController.getNumberRequest(), new StationCodeListener());
        }else{
            listController.setCode(this.stationCode);
            spiceManager.execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
        }
    }

    private class StationCodeListener extends AbstractListener<ListWrapper> {
        @Override
        public void onRequestSuccess(final ListWrapper result) {
            List<String> data = result.getList();
            if(Utilities.isOneResult(data)){
                trainDetails = listController.computeData(data.get(0));
                listController.setCode(trainDetails[1]);
                stationCode = trainDetails[1];
                favouriteFragmentsUtils.toggleFavouriteIcon(trainNumber, stationCode);
                spiceManager.execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
            }else{
                final String[][] dataMatrix = listController.computeMatrix(data);
                String[] choices = listController.computeChoices(dataMatrix);
                dialogBuilder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        trainDetails = dataMatrix[which];
                        listController.setCode(dataMatrix[which][1]);
                        spiceManager.execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
                        favouriteFragmentsUtils.toggleFavouriteIcon(trainNumber, stationCode);
                        dialog.dismiss();
                    }
                }).show();
            }
        }

        @Override
        public Context getDialogContext() {
            return getActivity();
        }
    }

    private class TrainResultListener extends AbstractListener<Train>{
        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(Train trainResponse) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(trainResponse.getCategoria() + " " + trainResponse.getNumeroTreno());

            trainResponse.setProgress(listController.getProgress(trainResponse));

            trainDetails = new String[2];
            trainDetails[0] = trainResponse.getNumeroTreno().toString();
            trainDetails[1] = trainResponse.getIdOrigine();
            favouriteFragmentsUtils.toggleFavouriteIcon(trainNumber, stationCode);

            fermateList.clear();
            fermateList.addAll(trainResponse.getFermate());
            adapter.notifyDataSetChanged();

            textDelay.setVisibility(View.VISIBLE);
            textLastSeen.setVisibility(View.VISIBLE);
            textProgress.setVisibility(View.VISIBLE);
            info.setText(trainResponse.getSubTitle());
            delay.setText(trainResponse.getRitardo().toString() + "'");
            progress.setText(trainResponse.getProgress());
            lastSeenTime.setText(trainResponse.getCompOraUltimoRilevamento());
            lastSeenStation.setText(trainResponse.getStazioneUltimoRilevamento());
        }
    }
}
