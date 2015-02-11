package com.example.lisamazzini.train_app.GUI.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.example.lisamazzini.train_app.GUI.Fragment.FavouriteTrainListFragment;
import com.example.lisamazzini.train_app.R;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class FavouriteTrainListActivity extends AbstractBaseActivity{

    FavouriteTrainListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_train_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Favourite Trains");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FragmentManager fragmentMan = getSupportFragmentManager();
        fragmentMan.beginTransaction().replace(R.id.container, FavouriteTrainListFragment.newInstance());
        fragment = (FavouriteTrainListFragment)getSupportFragmentManager().findFragmentById(R.id.favouriteTrainListFragment);
        fragment.makeRequest();

    }
}
