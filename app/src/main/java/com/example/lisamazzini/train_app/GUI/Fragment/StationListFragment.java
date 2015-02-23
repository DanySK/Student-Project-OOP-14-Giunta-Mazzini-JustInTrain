package com.example.lisamazzini.train_app.gui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lisamazzini.train_app.controller.favourites.FavouriteControllerStrategy;
import com.example.lisamazzini.train_app.network.AbstractListener;
import com.example.lisamazzini.train_app.controller.favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.controller.StationListController;
import com.example.lisamazzini.train_app.gui.adapter.StationListAdapter;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.model.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.List;

/**
 * Fragment che mostra la lista di stazioni e i dettagli del treno selezionato.
 *
 * @author lisamazzini
 */
public class StationListFragment extends AbstractFavouriteFragment {

    private StationListAdapter adapter;

    private StationListController listController;
    private final IFavouriteController favController = FavouriteTrainController.getInstance();

    private TextView info;
    private TextView delay;
    private TextView progress;
    private TextView lastSeenTime;
    private TextView lastSeenStation;
    private TextView textDelay;
    private TextView textProgress;
    private TextView textLastSeen;

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFavouriteController(new FavouriteControllerStrategy() {
            @Override
            public IFavouriteController getController() {
                return FavouriteTrainController.getInstance();
            }
        });

        setSpiceManager(new SpiceManager(UncachedSpiceService.class));
        final View layoutInflater = inflater.inflate(R.layout.fragment_station_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) layoutInflater.findViewById(R.id.recycler);

        info = (TextView) layoutInflater.findViewById(R.id.tInfo);
        delay = (TextView) layoutInflater.findViewById(R.id.tDelay);
        progress = (TextView) layoutInflater.findViewById(R.id.tProgress);
        lastSeenTime = (TextView) layoutInflater.findViewById(R.id.tLastSeenTime);
        lastSeenStation = (TextView) layoutInflater.findViewById(R.id.tLastSeenStation);
        textDelay = (TextView) layoutInflater.findViewById(R.id.lDelay);
        textLastSeen = (TextView) layoutInflater.findViewById(R.id.lLastSeen);
        textProgress = (TextView) layoutInflater.findViewById(R.id.lProgress);

        // TODO fa unico meodo per visible e invisible (toggler)
        textDelay.setVisibility(View.INVISIBLE);
        textLastSeen.setVisibility(View.INVISIBLE);
        textProgress.setVisibility(View.INVISIBLE);

        this.listController = new StationListController();
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        this.adapter = new StationListAdapter(listController.getFermateList());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.favController.setContext(getActivity().getApplicationContext());

        return layoutInflater;
    }


    @Override
    public final String[] getFavouriteForAdding() {
        return listController.getTrainDetails();
    }

    @Override
    public final String[] getFavouriteForRemoving() {
        return listController.getTrainDetails();
    }

    /**
     * Metodo che viene chiamato per eseguire una ricerca dati il numero del treno ed eventualmente il codice della stazione di origine.
     * @param trainNumber numero del treno
     * @param stationCode stazione di origine
     */
    public final void makeRequest(final String trainNumber, final String stationCode) {
        listController.setTrainNumber(trainNumber);
        if (stationCode == null) {
            getSpiceManager().execute(listController.getNumberRequest(), new StationCodeListener());
        } else {
            listController.setTrainCode(stationCode);
            getSpiceManager().execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
        }
    }

    private class StationCodeListener extends AbstractListener<ListWrapper> {
        @Override
        public void onRequestSuccess(final ListWrapper result) {
            final List<String> data = result.getList();
            if (Utilities.isOneResult(data)) {
                listController.setTrainDetails(listController.computeData(data.get(0)));
                listController.setTrainCode(listController.getTrainDetails()[1]);
                toggleFavouriteIcon(listController.getTrainNumber(), listController.getTrainCode());
                getSpiceManager().execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
            } else {
                final String[][] dataMatrix = listController.computeMatrix(data);
                final String[] choices = listController.computeChoices(dataMatrix);
                getDialogBuilder().setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        listController.setTrainDetails(dataMatrix[which]);
                        listController.setTrainCode(dataMatrix[which][1]);
                        getSpiceManager().execute(listController.getNumberAndCodeRequest(), new TrainResultListener());
                        toggleFavouriteIcon(listController.getTrainNumber(), listController.getTrainCode());
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

    private class TrainResultListener extends AbstractListener<Treno> {
        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final Treno trainResponse) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(trainResponse.getCategoria() + " " + trainResponse.getNumeroTreno());

            trainResponse.setProgress(listController.getProgress(trainResponse));

            String[] trainDetails = new String[2];
            trainDetails[0] = Long.toString(trainResponse.getNumeroTreno());
            trainDetails[1] = trainResponse.getIdOrigine();
            listController.setTrainDetails(trainDetails);
            toggleFavouriteIcon(listController.getTrainNumber(), listController.getTrainCode());

            listController.setFermateList(trainResponse);
            adapter.notifyDataSetChanged();

            textDelay.setVisibility(View.VISIBLE);
            textLastSeen.setVisibility(View.VISIBLE);
            textProgress.setVisibility(View.VISIBLE);
            info.setText(trainResponse.getSubTitle());
            delay.setText(Long.toString(trainResponse.getRitardo()) + "'");
            progress.setText(trainResponse.getProgress());
            lastSeenTime.setText(trainResponse.getCompOraUltimoRilevamento());
            lastSeenStation.setText(trainResponse.getStazioneUltimoRilevamento());
        }
    }
}
