package com.example.lisamazzini.train_app.gui.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.lisamazzini.train_app.gui.fragment.FavouriteTrainListFragment;
import com.example.lisamazzini.train_app.R;

public class FavouriteTrainListActivity extends AbstractBaseActivity {

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

    /**
     * Questa classe non prevede la ricezione di intent da altre parti dell'applicazione.*
     */
    @Override
    protected void getIntents() { }
}