package com.example.lisamazzini.train_app.GUI.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by lisamazzini on 11/02/15.
 */
public abstract interface IBaseActivity {

    public boolean onCreateOptionMenu(Menu menu);

    public boolean onOptionItemSelected(MenuItem item);

}
