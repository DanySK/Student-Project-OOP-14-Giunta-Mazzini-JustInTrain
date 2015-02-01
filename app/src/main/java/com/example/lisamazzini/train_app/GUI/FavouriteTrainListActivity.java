package com.example.lisamazzini.train_app.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lisamazzini.train_app.*;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.FavouriteTrainListController;
import com.example.lisamazzini.train_app.GUI.Adapter.FavTrainAdapter;
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Notification.NotificationService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class FavouriteTrainListActivity extends Activity{

    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    //private FavouriteTrainController favController = new FavouriteTrainController(getApplicationContext());
    //private FavouriteTrainListController listController = new FavouriteTrainListController(favController.getMap());

    private FavouriteTrainController favController;
    private FavouriteTrainListController listController;
    private RecyclerView favListView;
    private List<Train> favList = new LinkedList<>();

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
        Intent i = getIntent();



        favController = new FavouriteTrainController(getApplicationContext());
        listController = new FavouriteTrainListController(favController.getMap());

        this.favListView = (RecyclerView)findViewById(R.id.recycler);
        this.favListView.setLayoutManager(new LinearLayoutManager(this));
        this.favListView.setHasFixedSize(true);
        this.favListView.setItemAnimator(new DefaultItemAnimator());


        while(listController.hasAnotherFavourite()){
            spiceManager.execute(listController.getRequest(), new TrainRequestListener());
        }

    }


    private class TrainRequestListener implements RequestListener<Train> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(FavouriteTrainListActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
          favList.add(train);
          favListView.setAdapter(new FavTrainAdapter(favList));

        }
    }
}
