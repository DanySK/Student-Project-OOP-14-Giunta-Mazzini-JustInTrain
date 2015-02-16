package com.example.lisamazzini.train_app.GUI.Fragment;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public interface IFavouriteFragment {

    void onCreate(Bundle savedInstanceState);

    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    boolean onOptionsItemSelected(MenuItem item);

    FavouriteFragmentsUtils getFragmentUtils();
}