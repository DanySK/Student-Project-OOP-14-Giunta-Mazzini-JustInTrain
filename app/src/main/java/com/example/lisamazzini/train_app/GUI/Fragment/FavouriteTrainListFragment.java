package com.example.lisamazzini.train_app.GUI.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.lisamazzini.train_app.GUI.Adapter.FavTrainAdapter;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;

public class FavouriteTrainListFragment extends Fragment {

    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private IFavouriteController favController = FavouriteTrainController.getInstance();
    private FavouriteTrainListController listController;
    private RecyclerView favListView;
    private List<Train> favList = new LinkedList<>();
    private LinearLayoutManager manager;
    private FavTrainAdapter adapter;

    public static FavouriteTrainListFragment newInstance() {
        return new FavouriteTrainListFragment();
    }

    public FavouriteTrainListFragment() {
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

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layoutInfalter = inflater.inflate(R.layout.fragment_favourite_train_list, container, false);

        favController = FavouriteTrainController.getInstance();
        favController.setContext(getActivity().getApplicationContext());
        listController = new FavouriteTrainListController((java.util.Map<String, String>) favController.getFavouritesAsMap());

        this.adapter = new FavTrainAdapter(favList);
        this.favListView = (RecyclerView)layoutInfalter.findViewById(R.id.favouriteRecycler);
        this.manager = new LinearLayoutManager(getActivity());

        this.favListView.setLayoutManager(this.manager);
        this.favListView.setAdapter(adapter);
        this.favListView.setItemAnimator(new DefaultItemAnimator());


        return layoutInfalter;
    }

    public void makeRequest() {
        while(listController.hasAnotherFavourite()) {
            spiceManager.execute(listController.getRequest(), new TrainRequestListener());
        }
    }

    private class TrainRequestListener extends AbstractListener<Train> {

        @Override
        public Context getDialogContext() {
            return getActivity();
        }

        @Override
        public void onRequestSuccess(Train trainResponse) {
            favList.add(trainResponse);
//            Log.d("cazzi", favList.get(0).getCategoria() + " " + favList.get(0).getCompDurata());
            adapter.notifyDataSetChanged();
        }
    }



}
