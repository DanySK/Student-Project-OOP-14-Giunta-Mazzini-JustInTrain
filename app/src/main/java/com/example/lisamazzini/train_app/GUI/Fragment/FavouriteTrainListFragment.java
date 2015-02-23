package com.example.lisamazzini.train_app.gui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lisamazzini.train_app.network.AbstractListener;
import com.example.lisamazzini.train_app.controller.FavouriteTrainListController;
import com.example.lisamazzini.train_app.controller.favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.gui.adapter.FavouriteTrainListAdapter;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.Map;

/**
 * Fragment che mostra la lista di treni favoriti.
 *
 * @author lisamazzini
 */
public class FavouriteTrainListFragment extends AbstractRobospiceFragment {


    private FavouriteTrainListController favouriteTrainListController;
    private FavouriteTrainListAdapter adapter;

    /**
     * Metodo che restituisce una nuova istanza del fragment.
     * @return fragment
     */
    public static FavouriteTrainListFragment newInstance() {
        return new FavouriteTrainListFragment();
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {

        final View layoutInfalter = inflater.inflate(R.layout.fragment_favourite_train_list, container, false);
        setSpiceManager(new SpiceManager(UncachedSpiceService.class));


        this.favouriteTrainListController = new FavouriteTrainListController(getActivity());

        final RecyclerView recyclerView = (RecyclerView) layoutInfalter.findViewById(R.id.favouriteRecycler);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        this.adapter = new FavouriteTrainListAdapter(favouriteTrainListController.getFavouriteTrainsList());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return layoutInfalter;
    }

    /**
     * Metodo chiamato per effetturare una richiesta secondo i criteri selezionati.
     */
    public final void makeRequest() {
        while (favouriteTrainListController.hasAnotherFavourite()) {
            getSpiceManager().execute(favouriteTrainListController.getRequest(), new TrainRequestListener());
        }
    }

    private class TrainRequestListener extends AbstractListener<Treno> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(final Treno trainResponse) {
            favouriteTrainListController.addToFavouriteTrainList(trainResponse);
            adapter.notifyDataSetChanged();
        }
    }
}
