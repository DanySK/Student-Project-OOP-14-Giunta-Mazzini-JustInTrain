package com.example.lisamazzini.train_app.gui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Fragment che mostra la lista di soluzioni disponibili.
 *
 * @author albertogiunta
 */
public class JourneyResultsFragment extends AbstractFavouriteFragment {

    private final JourneyListController controller = new JourneyListController();
    private RecyclerView recyclerView;
    private final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    private final JourneyListAdapter adapter = new JourneyListAdapter(controller.getPartialPlainSolutions());

    /**
     * Metodo che restituisce una nuova istanza del fragment.
     * @return fragment
     */
    public static JourneyResultsFragment newInstance() {
        return new JourneyResultsFragment();
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View layoutInflater = inflater.inflate(R.layout.fragment_journey_results, container, false);

        setFavouriteController(new FavouriteControllerStrategy() {
            @Override
            public IFavouriteController getController() {
                return FavouriteJourneyController.getInstance();
            }
        });
        setSpiceManager(new SpiceManager(UncachedSpiceService.class));
        this.recyclerView = (RecyclerView) layoutInflater.findViewById(R.id.cardListFragment);
        this.adapter.notifyDataSetChanged();
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        resetScrollListener();
        return layoutInflater;
    }


    private void resetScrollListener() {
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(final int currentPage) {
                getSpiceManager().execute(new JourneyTrainRequest(controller.getPlainSolutions(false)), new JourneyTrainRequestListener());
            }
        });
    }

    /**
     * Metodo invocato per resettare la gui (azzera la lista) e resettare le lo spiceManager.
     */
    public final void resetGui() {
        controller.clearPartialPlainSolutionList();
        resetScrollListener();
        adapter.notifyDataSetChanged();
        super.resetRequests();
    }

    private void setToolbarTitle() {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
                                                        Utilities.getShorterString(controller.getDepartureStation()) +
                                                        " • " +
                                                        Utilities.getShorterString(controller.getArrivalStation()));
    }


    /**
     * Metodo che resetta la il contorller e la gui e lancia le richieste in base alla modalità di richiesta.
     * @param userRequestType modalità di richiesta (WITH IDS oppure WITH STATIONS)
     * @param requestedTime data e ora a cui effettuare la richiesta
     * @param isCustomTime boolean utile a sapere se l'utente ha selezionato o meno un orario, e fare la ricerca di conseguenza
     * @param departureAndArrivalData stringhe contenenti i dati con cui effettuare la richiesta
     */
    public final void makeRequest(final String userRequestType, final String requestedTime, final boolean isCustomTime, final String... departureAndArrivalData) {
        resetGui();
        controller.setRequestedTime(requestedTime);
        controller.setCustomTime(isCustomTime);
        if (userRequestType.equals(Constants.WITH_IDS)) {
            super.resetRequests();
            controller.setDepartureID(departureAndArrivalData[0]);
            controller.setArrivalID(departureAndArrivalData[1]);
            getSpiceManager().execute(new JourneyRequest(controller.getDepartureID(), controller.getArrivalID(), requestedTime), new JourneyRequestListener());
            Toast.makeText(getActivity(), "Ricerca in corso...", Toast.LENGTH_SHORT).show();
        } else if (userRequestType.equals(Constants.WITH_STATIONS)) {
            controller.setDepartureStation(departureAndArrivalData[0]);
            controller.setArrivalStation(departureAndArrivalData[1]);
            getSpiceManager().execute(new JourneyDataRequest(controller.getDepartureStation()), new DepartureDataRequestListenter());
            Toast.makeText(getActivity(), "Ricerca in corso...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public final String[] getFavouriteForAdding() {
        return new String[]{controller.getDepartureID(), controller.getArrivalID(), Utilities.getShorterString(controller.getDepartureStation()).concat(" • ").concat(Utilities.getShorterString(controller.getArrivalStation()))};
    }

    @Override
    public final String[] getFavouriteForRemoving() {
        return new String[]{controller.getDepartureID(), controller.getArrivalID()};
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Inner class che viene eseguito in risposta a una JourneyDataRequest.
     * Se questa ha successo, e se ci sono più risultati disponibili per la stringa inserita, verrà mostrato all'utente
     * un popup che gli permetterà di scegliere la stazione giusta. altrimenti viene settato in automatico.
     * Viene poi eseguita la richiesta per il resto dei dati (stazione di arrivo).
     */
    private class DepartureDataRequestListenter extends AbstractListener<ListWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final ListWrapper lista) {
            final List<String> data = lista.getList();

            if (Utilities.isOneResult(data)) {
                String[] choices = controller.splitData(lista.getList().get(0));
                controller.setDepartureStation(choices[0]);
                controller.setDepartureID(choices[1]);
                getSpiceManager().execute(new JourneyDataRequest(controller.getArrivalStation()), new ArrivalDataRequestListener());
            } else {
                final String[][] choices = controller.getTableForMultipleResults(data);
                getDialogBuilder().setSingleChoiceItems(choices[0], -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        controller.setDepartureID(choices[1][which]);
                        controller.setDepartureStation(choices[0][which]);
                        getSpiceManager().execute(new JourneyDataRequest(controller.getArrivalStation()), new ArrivalDataRequestListener());
                        dialog.dismiss();
                    }
                }).show();
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Inner class che viene eseguito in risposta a una JourneyDataRequest.
     * Se questa ha successo, e se ci sono più risultati disponibili per la stringa inserita, verrà mostrato all'utente
     * un popup che gli permetterà di scegliere la stazione giusta. altrimenti viene settato in automatico.
     * Ora che ci sono i dati sia di partenza che arrivo è possibile eseguire la richiesta vera e propria, da cui si otterrà la lista di soluzioni.
     * Qui viene anche settato il titolo e togglato il pulsante dei preferiti.
     */
    private class ArrivalDataRequestListener extends AbstractListener<ListWrapper> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final ListWrapper lista) {

            final List<String> data = lista.getList();

            if (Utilities.isOneResult(data)) {
                String[] choices = controller.splitData(lista.getList().get(0));
                controller.setArrivalStation(choices[0]);
                controller.setArrivalID(choices[1]);
                toggleFavouriteIcon(controller.getDepartureID(), controller.getArrivalID());
                setToolbarTitle();
                getSpiceManager().execute(new JourneyRequest(controller.getDepartureID(), controller.getArrivalID(), controller.getRequestedTime()), new JourneyRequestListener());
            } else {
                final String[][] choices = controller.getTableForMultipleResults(data);
                getDialogBuilder().setSingleChoiceItems(choices[0], -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        controller.setArrivalID(choices[1][which]);
                        controller.setArrivalStation(choices[0][which]);
                        toggleFavouriteIcon(controller.getDepartureID(), controller.getArrivalID());
                        setToolbarTitle();
                        getSpiceManager().execute(new JourneyRequest(controller.getDepartureID(), controller.getArrivalID(), controller.getRequestedTime()), new JourneyRequestListener());
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
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final Tragitto tragitto) {
            controller.buildPlainSolutions(tragitto);
            getSpiceManager().execute(new JourneyTrainRequest(controller.getPlainSolutions(controller.isCustomTime())), new JourneyTrainRequestListener());
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
            Log.d("cazzi", "plain " + plainSolutions.getList().size());
            adapter.notifyDataSetChanged();
        }
    }
}
