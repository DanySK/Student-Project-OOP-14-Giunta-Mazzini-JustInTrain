package com.example.lisamazzini.train_app.gui.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.lisamazzini.train_app.gui.fragment.FavouriteTrainListFragment;
import com.example.lisamazzini.train_app.R;

/**
 * Classe che ospita il fragment per la visualizzazione della lista di treni favoriti.
 *
 * @author lisamazzini
 */
public class FavouriteTrainListActivity extends AbstractBaseActivity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_train_list);

        super.getToolbar();

        final FragmentManager fragmentMan = getSupportFragmentManager();
        fragmentMan.beginTransaction().replace(R.id.container, new FavouriteTrainListFragment());
        final FavouriteTrainListFragment fragment = (FavouriteTrainListFragment) getSupportFragmentManager().findFragmentById(R.id.favouriteTrainListFragment);
        fragment.makeRequest();
    }

    @Override
    protected final String setToolbarTitle() {
        return "Treni preferiti";
    }

    /**
     * Questa classe non prevede la ricezione di intent da altre parti dell'applicazione.
     */
    @Override
    protected final void getIntents() {
        throw new UnsupportedOperationException();
    }
}