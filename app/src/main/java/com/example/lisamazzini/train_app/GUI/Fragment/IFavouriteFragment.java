package com.example.lisamazzini.train_app.GUI.Fragment;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.Controller.FavouriteFragmentController;

public interface IFavouriteFragment extends IBaseFragment {

    void onCreate(Bundle savedInstanceState);

    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    boolean onOptionsItemSelected(MenuItem item);

    FavouriteFragmentController getFragmentUtils();


}
    FavouriteFragmentsUtils getFragmentUtils();
}