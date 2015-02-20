package com.example.lisamazzini.train_app.gui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.lisamazzini.train_app.gui.fragment.AchievementListFragment;
import com.example.lisamazzini.train_app.R;

/**
 * Classe che ospita il fragment per la visualizzazione di una lista di achievements.
 *
 * @author lisamazzini
 */
public class AchievementListActivity extends AbstractBaseActivity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_list);

        super.getToolbar();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, AchievementListFragment.newInstance());
        //AchievementListFragment fragment = (AchievementListFragment)getSupportFragmentManager().findFragmentById(R.id.achievementListFragment);

    }

    @Override
    protected final String setToolbarTitle() {
        return "Achievement sbloccati!";
    }

    /**
     * Questa classe non prevede la ricezione di intent da altre parti dell'applicazione.
     */
    @Override
    protected final void getIntents() {
        throw new UnsupportedOperationException();
    }
}
