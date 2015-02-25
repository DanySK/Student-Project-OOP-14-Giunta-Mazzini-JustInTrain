package com.example.lisamazzini.train_app.gui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.lisamazzini.train_app.gui.fragment.AchievementListFragment;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.model.TextConstants;

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
        fragmentManager.beginTransaction().replace(R.id.container, new AchievementListFragment());
    }

    @Override
    protected final String setToolbarTitle() {
        return TextConstants.TOOLBAR_ACHIEVEMENTS;
    }

    /**
     * Questa classe non prevede la ricezione di intent da altre parti dell'applicazione.
     */
    @Override
    protected final void getIntents() {
        throw new UnsupportedOperationException();
    }
}
