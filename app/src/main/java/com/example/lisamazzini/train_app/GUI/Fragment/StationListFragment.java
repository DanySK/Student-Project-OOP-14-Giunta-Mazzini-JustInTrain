package com.example.lisamazzini.train_app.GUI.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.lisamazzini.train_app.Network.AbstractListener;
import com.example.lisamazzini.train_app.Controller.FavouriteFragmentController;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.GUI.Adapter.StationListAdapter;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Model.Treno.Treno;
import com.example.lisamazzini.train_app.Model.Treno.Fermate;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StationListFragment extends AbstractRobospiceFragment implements IBaseFragment, IFavouriteFragment {

    private RecyclerView recyclerView;
    private StationListAdapter adapter;
    private LinearLayoutManager manager;

    private StationListController listController;
    private IFavouriteController favController = FavouriteTrainController.getInstance();
    private FavouriteFragmentController favouriteFragmentController;

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

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
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

        this.listController = new StationListController();
        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new StationListAdapter(listController.getFermateList());
        adapter.notifyDataSetChanged();

        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.favouriteFragmentController = new FavouriteFragmentController(FavouriteTrainController.getInstance());
        this.favouriteFragmentController.setContext(getActivity());

        this.favController.setContext(getActivity().getApplicationContext());

        return layoutInflater;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        favouriteFragmentController.setMenu(this.menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        menu = favouriteFragmentController.onOptionsItemSelected(item, listController.getTrainDetails(), getActivity());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public FavouriteFragmentController getFragmentUtils() {
        return this.favouriteFragmentController;
    }

    public void makeRequest(final String trainNumber, final String stationCode) {
        listController.setTrainNumber(trainNumber);
        if(stationCode == null) {
            spiceManager.execute(listController.getNumberRequest(), new StationCodeListener());
        }else{
            listController.setTrainCode(stationCode);
            spiceManager.execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
        }
    }

    private class StationCodeListener extends AbstractListener<ListWrapper> {
        @Override
        public void onRequestSuccess(final ListWrapper result) {
            List<String> data = result.getList();
            if(Utilities.isOneResult(data)){
                listController.setTrainDetails(listController.computeData(data.get(0)));
                listController.setTrainCode(listController.getTrainDetails()[1]);
                favouriteFragmentController.toggleFavouriteIcon(listController.getTrainNumber(), listController.getTrainCode());
                spiceManager.execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
            }else{
                final String[][] dataMatrix = listController.computeMatrix(data);
                String[] choices = listController.computeChoices(dataMatrix);
                dialogBuilder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        listController.setTrainDetails(dataMatrix[which]);
                        listController.setTrainCode(dataMatrix[which][1]);
                        spiceManager.execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
                        favouriteFragmentController.toggleFavouriteIcon(listController.getTrainNumber(), listController.getTrainCode());
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

    private class TrainResultListener extends AbstractListener<Treno>{
        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final Treno trainResponse) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(trainResponse.getCategoria() + " " + trainResponse.getNumeroTreno());

            trainResponse.setProgress(listController.getProgress(trainResponse));

            String[] trainDetails = new String[2];
            trainDetails[0] = trainResponse.getNumeroTreno().toString();
            trainDetails[1] = trainResponse.getIdOrigine();
            listController.setTrainDetails(trainDetails);
            favouriteFragmentController.toggleFavouriteIcon(listController.getTrainNumber(), listController.getTrainCode());

            listController.setFermateList(trainResponse);
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
