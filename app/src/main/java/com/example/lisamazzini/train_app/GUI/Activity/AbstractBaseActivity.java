package com.example.lisamazzini.train_app.GUI.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.R;

/**
 * Created by lisamazzini on 11/02/15.
 */
public abstract class AbstractBaseActivity extends ActionBarActivity {

    protected Toolbar toolbar;

    protected void getToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(setToolbarTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract String setToolbarTitle();

    protected abstract void getIntents();
}
