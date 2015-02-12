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

public class JourneyResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private JourneyResultsAdapter journeyResultsAdapter;
    JourneyResultsController controller;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private List<PlainSolution> list = new LinkedList<>();
    private String departureStation;
    private String departureID;
    private String arrivalStation;
    private String arrivalID;
    private String requestedTime;
    private IFavouriteController favouriteController = FavouriteJourneyController.getInstance();
    private Menu menu;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;


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

        controller = new JourneyResultsController();

        resetScrollListener();

//        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions()), new JourneyTrainRequestListener());
//            }
//        });


//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                visibleItemCount = recyclerView.getChildCount();
//                totalItemCount = manager.getItemCount();
//                firstVisibleItem = manager.findFirstVisibleItemPosition();
//
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                    }
//                }
//
//                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
//                    Log.d("cazzi", "ho raggiunto la fine");
//                    loading = true;
//                }
//            }
//        });



        return layoutInflater;
    }

    public void resetScrollListener() {
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page) {
                spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions()), new JourneyTrainRequestListener());
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
    }

    private void setAsFavouriteIcon(boolean b) {
        menu.getItem(0).setVisible(!b);
        menu.getItem(1).setVisible(b);
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
        favouriteController.removeFavourite(departureID, arrivalID);
    }

    public void addFavourite() throws FavouriteException {
        favouriteController.addFavourite(departureID, arrivalID, departureStation, arrivalStation);
    }

    public void makeOuterRequestsWithStations(String departureStation, String arrivalStation, String requestedTime) {
        list.clear();
        journeyResultsAdapter.notifyDataSetChanged();
        resetScrollListener();
        makeRequestsWithStations(departureStation, arrivalStation, requestedTime);
    }

    public void makeOuterRequestsWithIDs(String departureID, String arrivalID, String requestedTime) {
        list.clear();
        journeyResultsAdapter.notifyDataSetChanged();
        resetScrollListener();
        makeRequestsWithIDs(departureID, arrivalID, requestedTime);
    }


    private void makeRequestsWithStations(String departureStation, String arrivalStation, String requestedTime) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.requestedTime = requestedTime;
        spiceManager.execute(new JourneyDataRequest(this.departureStation), new DepartureDataRequestListenter());
    }

    private void makeRequestsWithIDs(String departureID, String arrivalID, String requestedTime) {
        this.departureID = departureID;
        this.arrivalID = arrivalID;
        this.requestedTime = requestedTime;
        spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
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
                        toggleFavouriteIcon();
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
            toggleFavouriteIcon();
            controller.buildPlainSolutions(tragitto);
            spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions()), new JourneyTrainRequestListener());
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

    ///////////////////////////////////////////////////////////////////////////////////////

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
