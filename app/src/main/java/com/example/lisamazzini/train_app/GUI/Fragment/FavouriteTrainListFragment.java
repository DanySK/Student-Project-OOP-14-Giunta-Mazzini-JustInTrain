package com.example.lisamazzini.train_app.gui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lisamazzini.train_app.network.AbstractListener;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainListController;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.gui.Adapter.FavouriteTrainListAdapter;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.Map;


public class FavouriteTrainListFragment extends AbstractRobospiceFragment{


    private FavouriteTrainListController favouriteTrainListController;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private FavouriteTrainListAdapter adapter;

    public static FavouriteTrainListFragment newInstance() {
        return new FavouriteTrainListFragment();
    }

    public FavouriteTrainListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View layoutInfalter = inflater.inflate(R.layout.fragment_favourite_train_list, container, false);
        super.spiceManager = new SpiceManager(UncachedSpiceService.class);

        IFavouriteController favouriteController = FavouriteTrainController.getInstance();
        favouriteController.setContext(getActivity().getApplicationContext());
        this.favouriteTrainListController = new FavouriteTrainListController((Map<String, String>) favouriteController.getFavouritesAsMap());

        this.recyclerView = (RecyclerView)layoutInfalter.findViewById(R.id.favouriteRecycler);
        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new FavouriteTrainListAdapter(favouriteTrainListController.getFavouriteTrainsList());

        this.recyclerView.setLayoutManager(this.manager);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        return layoutInfalter;
    }

    public void makeRequest() {
        while(favouriteTrainListController.hasAnotherFavourite()) {
            spiceManager.execute(favouriteTrainListController.getRequest(), new TrainRequestListener());
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
