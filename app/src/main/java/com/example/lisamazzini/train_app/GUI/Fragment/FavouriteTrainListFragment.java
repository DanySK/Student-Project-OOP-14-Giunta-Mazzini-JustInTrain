package com.example.lisamazzini.train_app.GUI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainListController;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.GUI.Adapter.FavouriteTrainListAdapter;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FavouriteTrainListFragment extends AbstractRobospiceFragment {


    private FavouriteTrainListController favouriteTrainListController;
    private List<Train> favouriteTrainsList = new LinkedList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private FavouriteTrainListAdapter adapter;

    public static FavouriteTrainListFragment newInstance() {
        return new FavouriteTrainListFragment();
    }

    public FavouriteTrainListFragment() {
    }

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.spiceManager = new SpiceManager(UncachedSpiceService.class);

        IFavouriteController favouriteController = FavouriteTrainController.getInstance();
        favouriteController.setContext(getActivity().getApplicationContext());
        this.favouriteTrainListController = new FavouriteTrainListController((Map<String, String>) favouriteController.getFavouritesAsMap());

        View layoutInfalter = inflater.inflate(R.layout.fragment_favourite_train_list, container, false);
        this.recyclerView = (RecyclerView)layoutInfalter.findViewById(R.id.favouriteRecycler);
        this.manager = new LinearLayoutManager(getActivity());
        this.adapter = new FavouriteTrainListAdapter(favouriteTrainsList);

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

    private class TrainRequestListener extends AbstractListener<Train> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(Train trainResponse) {
            favouriteTrainsList.add(trainResponse);
            adapter.notifyDataSetChanged();
        }
    }
}
