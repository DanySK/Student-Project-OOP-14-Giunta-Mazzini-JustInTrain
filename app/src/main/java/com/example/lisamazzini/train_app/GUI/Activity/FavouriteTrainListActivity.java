package com.example.lisamazzini.train_app.GUI.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.lisamazzini.train_app.GUI.Fragment.FavouriteTrainListFragment;
import com.example.lisamazzini.train_app.R;

public class xFavouriteTrainListActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_train_list);

        super.getToolbar();

        final FragmentManager fragmentMan = getSupportFragmentManager();
        fragmentMan.beginTransaction().replace(R.id.container, FavouriteTrainListFragment.newInstance());
        final FavouriteTrainListFragment fragment = (FavouriteTrainListFragment) getSupportFragmentManager().findFragmentById(R.id.favouriteTrainListFragment);
        fragment.makeRequest();
    }

    @Override
    protected String setToolbarTitle() {
        return "Treni preferiti";
    }

    @Override
    protected void getIntents() { }
}