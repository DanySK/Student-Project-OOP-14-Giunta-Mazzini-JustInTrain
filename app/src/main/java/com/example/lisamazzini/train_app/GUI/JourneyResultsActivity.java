package com.example.lisamazzini.train_app.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Controller.JourneyResultsController2;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.R;

public class JourneyResultsActivity extends ActionBarActivity {

    private JourneyResultsFragment fragment;
    private IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();
    private Toolbar toolbar;
    private String departureStation;
    private String arrivalStation;
    private String requestedTime;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);

        Intent i = getIntent();
        this.departureStation = i.getStringExtra("departureStation");
        this.arrivalStation = i.getStringExtra("arrivalStation");
        this.requestedTime = i.getStringExtra("requestedTime");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(departureStation + " " + arrivalStation);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, JourneyResultsFragment.newInstance());
        fragment = (JourneyResultsFragment) getSupportFragmentManager().findFragmentById(R.id.journeyResultsFragment);

        fragment.makeRequestsWithStations(departureStation, arrivalStation, requestedTime);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        } else if (id == R.id.action_prefere) {
//            try {
//                favouriteJourneyController.addFavourite(actualJourneyIDs.get(0), actualJourneyIDs.get(1), actualJourneyIDs.get(2), actualJourneyIDs.get(3));
//                item.setVisible(false);
//                menu.getItem(2).setVisible(true);
//                restoreActionBar(menu);
//                Log.d("cazzi", "ho toccato un non preferito");
//            } catch (FavouriteException e) {
//                e.printStackTrace();
//            }
//        } else if (id == R.id.action_deprefere) {
//            favouriteJourneyController.removeFavourite(actualJourneyIDs.get(0).split(Constants.SEPARATOR)[0], actualJourneyIDs.get(0).split(Constants.SEPARATOR)[1]);
//            Log.d("cazzi", "rimuovo " + actualJourneyIDs.get(0).split(Constants.SEPARATOR)[0] + " " + actualJourneyIDs.get(0).split(Constants.SEPARATOR)[1]);
//            item.setVisible(false);
//            menu.getItem(1).setVisible(true);
//            Log.d("cazzi", "ho toccato un preferito");
//            restoreActionBar(menu);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
