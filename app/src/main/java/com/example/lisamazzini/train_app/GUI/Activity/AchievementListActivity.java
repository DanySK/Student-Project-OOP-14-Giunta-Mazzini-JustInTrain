package com.example.lisamazzini.train_app.gui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.lisamazzini.train_app.gui.fragment.AchievementListFragment;
import com.example.lisamazzini.train_app.R;

public class AchievementListActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_list);

        super.getToolbar();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, AchievementListFragment.newInstance());
        //AchievementListFragment fragment = (AchievementListFragment)getSupportFragmentManager().findFragmentById(R.id.achievementListFragment);

    }

    @Override
    protected String setToolbarTitle() {
        return "Achievement sbloccati!";
    }

    /**
     * Questa classe non prevede la ricezione di intent da altre parti dell'applicazione.
     */
    @Override
    protected void getIntents() { }
}
