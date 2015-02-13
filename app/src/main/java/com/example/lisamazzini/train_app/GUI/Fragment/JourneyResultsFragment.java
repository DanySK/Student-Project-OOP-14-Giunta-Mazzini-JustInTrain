package com.example.lisamazzini.train_app.GUI.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.JourneyDataRequest;
import com.example.lisamazzini.train_app.Controller.JourneyRequest;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController;
import com.example.lisamazzini.train_app.Controller.JourneyTrainRequest;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.GUI.Activity.MainActivity;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.GUI.EndlessRecyclerOnScrollListener;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.LinkedList;
import java.util.List;

public class JourneyResultsFragment extends AbstractRobospiceFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private JourneyResultsAdapter journeyResultsAdapter;
    private JourneyResultsController controller;
    private List<PlainSolution> list = new LinkedList<>();
    private String departureStation;
    private String departureID;
    private String arrivalStation;
    private String arrivalID;
    private String requestedTime;
    private IFavouriteController favouriteController = FavouriteJourneyController.getInstance();
    private Menu menu;
    private boolean isCustomTime;
    private FavouriteFragmentsUtils favouriteFragmentsUtils;

    public static JourneyResultsFragment newInstance() {
        return new JourneyResultsFragment();
    }

    public JourneyResultsFragment() {
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
        this.favouriteController.setContext(getActivity().getApplicationContext());
        this.favouriteFragmentsUtils = new FavouriteFragmentsUtils(FavouriteJourneyController.getInstance());

        View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);
        this.recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);

        this.manager = new LinearLayoutManager(getActivity());
        this.journeyResultsAdapter = new JourneyResultsAdapter(list);
        this.journeyResultsAdapter.notifyDataSetChanged();

        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(journeyResultsAdapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        controller = new JourneyResultsController();

        resetScrollListener();

        return layoutInflater;
    }

    public void resetScrollListener() {
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page) {
                spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions(false)), new JourneyTrainRequestListener());
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        this.favouriteFragmentsUtils.setMenu(this.menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menu = favouriteFragmentsUtils.onOptionsItemSelected(item, new String[]{departureID, arrivalID, departureStation, arrivalStation});
        return super.onOptionsItemSelected(item);
    }


    public void makeRequest(String userRequestType, String requestedTime, boolean isCustomTime, String... departureAndArrivalData) {
        list.clear();
        journeyResultsAdapter.notifyDataSetChanged();
        resetScrollListener();
        this.requestedTime = requestedTime;
        this.controller.setTime(this.requestedTime);
        this.isCustomTime = isCustomTime;
        if (userRequestType.equals(Constants.WITH_IDS)) {
            if (spiceManager.isStarted()) {
                spiceManager.dontNotifyAnyRequestListeners();
                spiceManager.shouldStop();
                spiceManager.start(getActivity());
            }
            this.departureID = departureAndArrivalData[0];
            this.arrivalID = departureAndArrivalData[1];
            spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
        } else if (userRequestType.equals(Constants.WITH_STATIONS)) {
            this.departureStation = departureAndArrivalData[0];
            this.arrivalStation = departureAndArrivalData[1];
            spiceManager.execute(new JourneyDataRequest(this.departureStation), new DepartureDataRequestListenter());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class DepartureDataRequestListenter extends AbstractListener<ListWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(ListWrapper lista) {
            List<String> data = lista.getList();
            if (data.size() == 1) {
                departureID = data.get(0).split("\\|S")[1];
                spiceManager.execute(new JourneyDataRequest(arrivalStation), new ArrivalDataRequestListener());
            } else {
                final String[][] dataMatrix = new String[data.size()][2];
                String[] choices = new String[data.size()];
                for (int i = 0 ; i < data.size(); i++){
                    dataMatrix[i] = controller.computeData(data.get(i));
                    choices[i] = controller.computeChoices(dataMatrix[i]);
                }
                dialogBuilder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        departureID = dataMatrix[which][1];
                        spiceManager.execute(new JourneyDataRequest(arrivalStation), new ArrivalDataRequestListener());
                        dialog.dismiss();
                    }
                }).show();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class ArrivalDataRequestListener extends AbstractListener<ListWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(ListWrapper lista) {
            List<String> data = lista.getList();
            if (data.size() == 1) {
                arrivalID = data.get(0).split("\\|S")[1];
                spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
            } else {
                final String[][] dataMatrix = new String[data.size()][2];
                String[] choices = new String[data.size()];
                for (int i = 0 ; i < data.size(); i++){
                    dataMatrix[i] = controller.computeData(data.get(i));
                    choices[i] = controller.computeChoices(dataMatrix[i]);
                }
                dialogBuilder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrivalID = dataMatrix[which][1];
                        spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
                        favouriteFragmentsUtils.toggleFavouriteIcon(departureID, arrivalID);
                        dialog.dismiss();
                    }
                }).show();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class JourneyRequestListener extends AbstractListener<Tragitto> {

        @Override
        public Context getDialogContext() {
            return getActivity().getApplicationContext();
        }

        @Override
        public void onRequestSuccess(Tragitto tragitto) {
            favouriteFragmentsUtils.toggleFavouriteIcon(departureID, arrivalID);
            controller.buildPlainSolutions(tragitto);
            spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions(isCustomTime)), new JourneyTrainRequestListener());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class JourneyTrainRequestListener extends AbstractListener<PlainSolutionWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(PlainSolutionWrapper plainSolutions) {
            list.addAll(plainSolutions.getList());
            journeyResultsAdapter.notifyDataSetChanged();
        }
    }
}
