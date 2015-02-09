package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
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

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.JourneyDataRequest;
import com.example.lisamazzini.train_app.Controller.JourneyRequest;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController2;
import com.example.lisamazzini.train_app.Controller.JourneyTrainRequest;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JourneyResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private JourneyResultsAdapter journeyResultsAdapter;
    JourneyResultsController2 controller;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private List<PlainSolution> list = new LinkedList<>();
    private String departureStation;
    private String departureID;
    private String arrivalStation;
    private String arrivalID;
    private String requestedTime;
    private boolean idsDownloaded;
    private IFavouriteController favouriteController = FavouriteJourneyController.getInstance();
    private Menu menu;


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
        favouriteController.setContext(getActivity().getApplicationContext());

        View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);
        recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);

        this.manager = new LinearLayoutManager(getActivity());
        this.journeyResultsAdapter = new JourneyResultsAdapter(list);
        journeyResultsAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(journeyResultsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        controller = new JourneyResultsController2();

        return layoutInflater;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
    }

    private void setAsFavouriteIcon(boolean b) {
        menu.getItem(1).setVisible(!b);
        menu.getItem(2).setVisible(b);
    }

    private void toggleFavouriteIcon() {
        if (favouriteController.isFavourite(departureID, arrivalID)) {
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
        favouriteController.removeFavourite(departureID, arrivalID);
    }

    public void addFavourite() throws FavouriteException {
        Log.d("cazzi", "aggiungo");
        favouriteController.addFavourite(departureID, arrivalID, departureStation, arrivalStation);
    }

    public boolean isIDsDownloaded() {
        return idsDownloaded;
    }

    public void makeRequestsWithStations(String departureStation, String arrivalStation, String requestedTime) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.requestedTime = requestedTime;
        spiceManager.execute(new JourneyDataRequest(this.departureStation), new DepartureDataRequestListenter());
    }

    public void makeRequestsWithIDs(String departureID, String arrivalID, String requestedTime) {
        this.departureID = departureID;
        this.arrivalID = arrivalID;
        this.requestedTime = requestedTime;
        spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class DepartureDataRequestListenter implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException.getCause() instanceof InvalidStationException) {
                Log.d("cazzi", "sbagliata partenza");
            }
        }

        @Override
        public void onRequestSuccess(String s) {
            departureID = s.split("\\|S")[1];
            spiceManager.execute(new JourneyDataRequest(arrivalStation), new ArrivalDataRequestListener());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class ArrivalDataRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException.getCause() instanceof InvalidStationException) {
                Log.d("cazzi", "sbagliata arrivo");
            }
        }

        @Override
        public void onRequestSuccess(String s) {
            arrivalID = s.split("\\|S")[1];
            idsDownloaded = true;
            toggleFavouriteIcon();
            spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class JourneyRequestListener implements RequestListener<Tragitto> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d("cazzi", "altro errore");
        }

        @Override
        public void onRequestSuccess(Tragitto tragitto) {
            controller.buildPlainSolutions(tragitto);
            spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions()), new JourneyTrainRequestListener());
//            journeyResultsAdapter = new JourneyResultsAdapter(controller.getPlainSolutions());
//            recyclerView.setAdapter(journeyResultsAdapter);
//            journeyResultsAdapter.notifyDataSetChanged();
        }
    }

    private class JourneyTrainRequestListener implements RequestListener<PlainSolutionWrapper> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(PlainSolutionWrapper plainSolutions) {
            list.clear();
            list.addAll(plainSolutions.getList());
            journeyResultsAdapter.notifyDataSetChanged();
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
