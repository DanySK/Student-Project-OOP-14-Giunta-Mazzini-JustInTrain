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
public abstract class AbstractBaseActivity extends ActionBarActivity implements IBaseActivity{

    Toolbar toolbar;

    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d("cazzi", "actiivty");
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_prefere) {
            return false;
        } else if (id == R.id.action_deprefere) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
