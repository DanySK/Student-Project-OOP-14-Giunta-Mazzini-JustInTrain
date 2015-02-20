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
import android.widget.Toast;

import com.example.lisamazzini.train_app.controller.EndlessRecyclerOnScrollListener;
import com.example.lisamazzini.train_app.controller.favourites.FavouriteControllerStrategy;
import com.example.lisamazzini.train_app.controller.JourneyListController;
import com.example.lisamazzini.train_app.gui.adapter.JourneyListAdapter;
import com.example.lisamazzini.train_app.network.AbstractListener;
import com.example.lisamazzini.train_app.controller.favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.network.data.JourneyDataRequest;
import com.example.lisamazzini.train_app.network.total.JourneyRequest;
import com.example.lisamazzini.train_app.network.total.JourneyTrainRequest;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.model.tragitto.Tragitto;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.List;

public class JourneyResultsFragment extends AbstractFavouriteFragment {

    private final JourneyListController controller = new JourneyListController();
    private RecyclerView recyclerView;
    private final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    private JourneyListAdapter adapter = new JourneyListAdapter(controller.getPartialPlainSolutions());

    public static JourneyResultsFragment newInstance() {
        return new JourneyResultsFragment();
    }

    public JourneyResultsFragment() {
    }

//    @Override
//    public void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);

        setFavouriteController(new FavouriteControllerStrategy() {
            @Override
            public IFavouriteController getController() {
                return FavouriteJourneyController.getInstance();
            }
        });
        super.spiceManager = new SpiceManager(UncachedSpiceService.class);
//        this.favouriteController.setContext(getActivity().getApplicationContext());
        this.recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.cardListFragment);
        this.adapter.notifyDataSetChanged();
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
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

    public void makeRequest(final String userRequestType, final String requestedTime, final boolean isCustomTime, final String... departureAndArrivalData) {
        controller.clearPartialPlainSolutionList();
        adapter.notifyDataSetChanged();
        resetScrollListener();
        controller.setRequestedTime(requestedTime);
        controller.setCustomTime(isCustomTime);
        if (userRequestType.equals(Constants.WITH_IDS)) {
            super.resetRequests();
            controller.setDepartureID(departureAndArrivalData[0]);
            controller.setArrivalID(departureAndArrivalData[1]);
            spiceManager.execute(new JourneyRequest(controller.getDepartureID(), controller.getArrivalID(), requestedTime), new JourneyRequestListener());
            Toast.makeText(getActivity(), "Ricerca in corso...", Toast.LENGTH_SHORT).show();
        } else if (userRequestType.equals(Constants.WITH_STATIONS)) {
            controller.setDepartureStation( departureAndArrivalData[0]);
            controller.setArrivalStation(departureAndArrivalData[1]);
            spiceManager.execute(new JourneyDataRequest(controller.getDepartureStation()), new DepartureDataRequestListenter());
            Toast.makeText(getActivity(), "Ricerca in corso...", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public String[] getFavouriteForAdding() {
        return new String[]{controller.getDepartureID(), controller.getArrivalID(), controller.getDepartureStation(), controller.getArrivalStation()};
    }

    @Override
    public String[] getFavouriteForRemoving() {
        return new String[]{controller.getDepartureID(), controller.getArrivalID()};
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class DepartureDataRequestListenter extends AbstractListener<ListWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final ListWrapper lista) {
            List<String> data = lista.getList();

            if (Utilities.isOneResult(data)) {
                controller.setDepartureID(controller.splitData(lista.getList().get(0))[1]);
                spiceManager.execute(new JourneyDataRequest(controller.getArrivalStation()), new ArrivalDataRequestListener());
            } else {
                final String[][] choices = controller.getTableForMultipleResults(data);
                getDialogBuilder().setSingleChoiceItems(choices[0], -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        controller.setDepartureID(choices[1][which]);
                        controller.setDepartureStation(choices[0][which]);
                        spiceManager.execute(new JourneyDataRequest(controller.getArrivalStation()), new ArrivalDataRequestListener());
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
        public void onRequestSuccess(final ListWrapper lista) {

            List<String> data = lista.getList();

            if (Utilities.isOneResult(data)) {
                controller.setArrivalID(controller.splitData(lista.getList().get(0))[1]);
                toggleFavouriteIcon(controller.getDepartureID(), controller.getArrivalID());
                spiceManager.execute(new JourneyRequest(controller.getDepartureID(), controller.getArrivalID(), controller.getRequestedTime()), new JourneyRequestListener());
            } else {
                final String[][] choices = controller.getTableForMultipleResults(data);
                getDialogBuilder().setSingleChoiceItems(choices[0], -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        controller.setArrivalID(choices[1][which]);
                        controller.setArrivalStation(choices[0][which]);
                        toggleFavouriteIcon(controller.getDepartureID(), controller.getArrivalID());
                        spiceManager.execute(new JourneyRequest(controller.getDepartureID(), controller.getArrivalID(), controller.getRequestedTime()), new JourneyRequestListener());
                        dialog.dismiss();
                    }
                }).show();
            }

            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(controller.getDepartureStation() + " -> " + controller.getArrivalStation());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class JourneyRequestListener extends AbstractListener<Tragitto> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final Tragitto tragitto) {
            controller.buildPlainSolutions(tragitto);
            spiceManager.execute(new JourneyTrainRequest(controller.getPlainSolutions(controller.isCustomTime())), new JourneyTrainRequestListener());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private class JourneyTrainRequestListener extends AbstractListener<PlainSolutionWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final PlainSolutionWrapper plainSolutions) {
            controller.addSolutions(plainSolutions.getList());
            adapter.notifyDataSetChanged();
        }
    }

    public void resetGui() {
        super.resetRequests();
        controller.clearPartialPlainSolutionList();
        adapter.notifyDataSetChanged();
    }
}
