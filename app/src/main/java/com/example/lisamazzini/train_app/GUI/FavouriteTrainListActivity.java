package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.lisamazzini.train_app.*;
import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainListController;
import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.GUI.Adapter.FavTrainAdapter;
import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class FavouriteTrainListActivity extends Activity{

    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

    private IFavouriteController favController = FavouriteTrainController.getInstance();
    private FavouriteTrainListController listController;
    private RecyclerView favListView;
    private List<NewTrain> favList = new LinkedList<>();

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_train_list);

        favController = FavouriteTrainController.getInstance();
        favController.setContext(getApplicationContext());
        listController = new FavouriteTrainListController((java.util.Map<String, String>) favController.getFavouritesAsMap());

        this.favListView = (RecyclerView)findViewById(R.id.recycler);
        this.favListView.setLayoutManager(new LinearLayoutManager(this));
        this.favListView.setHasFixedSize(true);
        this.favListView.setItemAnimator(new DefaultItemAnimator());


        while(listController.hasAnotherFavourite()) {
            spiceManager.execute(listController.getRequest(), new TrainRequestListener());
        }
    }


    private class TrainRequestListener extends AbstractListener<NewTrain> {

        @Override
        public Context getContext() {
            return FavouriteTrainListActivity.this;
        }

        @Override
        public void onRequestSuccess(NewTrain trainResponse) {
            favList.add(trainResponse);
            favListView.setAdapter(new FavTrainAdapter(favList));
        }
    }
}
