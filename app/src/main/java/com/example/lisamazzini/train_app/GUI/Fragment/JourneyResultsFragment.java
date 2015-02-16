package com.example.lisamazzini.train_app.GUI.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.DataRequests.JourneyDataRequest;
import com.example.lisamazzini.train_app.Controller.TotalRequests.JourneyRequest;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController;
import com.example.lisamazzini.train_app.Controller.TotalRequests.JourneyTrainRequest;
import com.example.lisamazzini.train_app.GUI.Adapter.JourneyResultsAdapter;
import com.example.lisamazzini.train_app.GUI.EndlessRecyclerOnScrollListener;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;

public class JourneyResultsFragment extends AbstractRobospiceFragment implements IBaseFragment, IFavouriteFragment {

    private Menu menu;
    private RecyclerView recyclerView;
    private final List<PlainSolution> plainSolutionList = new LinkedList<>();
    private final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    private JourneyResultsAdapter adapter = new JourneyResultsAdapter(plainSolutionList);

    private final JourneyResultsController controller = new JourneyResultsController();
    private final FavouriteFragmentsUtils favouriteFragmentsUtils = new FavouriteFragmentsUtils(FavouriteJourneyController.getInstance());
    private final IFavouriteController favouriteController = FavouriteJourneyController.getInstance();

    private boolean isCustomTime;
    private String departureID, departureStation, arrivalID, arrivalStation, requestedTime;

    public static JourneyResultsFragment newInstance() {
        return new JourneyResultsFragment();
    }

    public JourneyResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);

        super.spiceManager = new SpiceManager(UncachedSpiceService.class);
        this.favouriteController.setContext(getActivity().getApplicationContext());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        this.favouriteFragmentsUtils.setMenu(this.menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        favouriteFragmentsUtils.setContext(getActivity());
        this.menu = favouriteFragmentsUtils.onOptionsItemSelected(item, new String[]{departureID, arrivalID, departureStation, arrivalStation}, getActivity());
        return super.onOptionsItemSelected(item);
    }


    public void makeRequest(String userRequestType, String requestedTime, boolean isCustomTime, String... departureAndArrivalData) {
        plainSolutionList.clear();
        adapter.notifyDataSetChanged();
        resetScrollListener();
        this.requestedTime = requestedTime;
        this.controller.setTime(this.requestedTime);
        this.isCustomTime = isCustomTime;
        if (userRequestType.equals(Constants.WITH_IDS)) {
            super.resetRequests();
            this.departureID = departureAndArrivalData[0];
            this.arrivalID = departureAndArrivalData[1];
            spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
            Toast.makeText(getActivity(), "Ricerca in corso...", Toast.LENGTH_SHORT).show();
        } else if (userRequestType.equals(Constants.WITH_STATIONS)) {
            this.departureStation = departureAndArrivalData[0];
            this.arrivalStation = departureAndArrivalData[1];
            spiceManager.execute(new JourneyDataRequest(this.departureStation), new DepartureDataRequestListenter());
            Toast.makeText(getActivity(), "Ricerca in corso...", Toast.LENGTH_SHORT).show();
        }
    }

    public FavouriteFragmentsUtils getFragmentUtils() {
        return favouriteFragmentsUtils;
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

            if (Utilities.isOneResult(data)) {
                departureID = controller.splitData(lista.getList().get(0))[1];
                spiceManager.execute(new JourneyDataRequest(arrivalStation), new ArrivalDataRequestListener());
            } else {
                final String[][] choices = controller.getTableForMultipleResults(data);
                dialogBuilder.setSingleChoiceItems(choices[0], -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        departureID = choices[1][which];
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

            if (Utilities.isOneResult(data)) {
                arrivalID = controller.splitData(lista.getList().get(0))[1];
                favouriteFragmentsUtils.toggleFavouriteIcon(departureID, arrivalID);
                spiceManager.execute(new JourneyRequest(departureID, arrivalID, requestedTime), new JourneyRequestListener());
            } else {
                final String[][] choices = controller.getTableForMultipleResults(data);
                dialogBuilder.setSingleChoiceItems(choices[0], -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrivalID = choices[1][which];
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
            plainSolutionList.addAll(plainSolutions.getList());
            adapter.notifyDataSetChanged();
        }
    }
}
