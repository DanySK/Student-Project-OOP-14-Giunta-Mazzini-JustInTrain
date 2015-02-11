package com.example.lisamazzini.train_app.GUI.Activity;

import android.support.v4.app.*;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.lisamazzini.train_app.GUI.Fragment.AchievementListFragment;
import com.example.lisamazzini.train_app.R;

public class AchievementListActivity extends ActionBarActivity {

    private AchievementListFragment fragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Achievement sbloccati!");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, AchievementListFragment.newInstance());
        fragment = (AchievementListFragment)getSupportFragmentManager().findFragmentById(R.id.achievementListFragment);
    }
}
