package com.example.lisamazzini.train_app.GUI.Fragment;


import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.R;

public abstract class AbstractFavouriteFragment extends AbstractRobospiceFragment {

    private MenuItem favItem;
    private MenuItem notFavItem;
    private IFavouriteController favouriteController;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        setMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().finish();
            } else if (id == R.id.action_prefere) {
                Toast.makeText(getActivity().getApplicationContext(), "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();
                favouriteController.addFavourite(getFavouriteForAdding());
                setAsFavouriteIcon(true);
            } else if (id == R.id.action_deprefere) {
                Toast.makeText(getActivity().getApplicationContext(), "Rimosso dai preferiti", Toast.LENGTH_SHORT).show();
                favouriteController.removeFavourite(getFavouriteForRemoving());
                setAsFavouriteIcon(false);
            }
        } catch (FavouriteException e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setMenu(Menu menu) {
        this.favItem = menu.findItem(R.id.action_deprefere);
        this.notFavItem = menu.findItem(R.id.action_prefere);
    }

    public void toggleFavouriteIcon(String reqData1, String reqData2) {
        if (favouriteController.isFavourite(reqData1, reqData2)) {
            setAsFavouriteIcon(true);
        } else {
            setAsFavouriteIcon(false);
        }
    }

    public void setAsFavouriteIcon(boolean b) {
        this.favItem.setVisible(b);
        this.notFavItem.setVisible(!b);
    }

    public void setAllEnabled(boolean b) {
        this.favItem.setVisible(b);
        this.notFavItem.setVisible(b);
    }

    public void setFavouriteController(FavouriteControllerStrategy strategy) {
        favouriteController = strategy.getController();
        favouriteController.setContext(getActivity());
    }

    public abstract String[] getFavouriteForAdding();

    public abstract String[] getFavouriteForRemoving();



}
