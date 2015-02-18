package com.example.lisamazzini.train_app.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.R;


public class FavouriteFragmentController {

    private final IFavouriteController favouriteController;
    private Menu menu;
    private MenuItem favItem;
    private MenuItem notFavItem;
    private Context context;


    public FavouriteFragmentController(final IFavouriteController controller) {
        this.favouriteController = controller;
    }

    public void setMenu(final Menu menu) {
        this.menu = menu;
        this.favItem = menu.findItem(R.id.action_deprefere);
        this.notFavItem = menu.findItem(R.id.action_prefere);
    }

    public void setContext(final Context context){
        this.context = context;
    }

    public Menu toggleFavouriteIcon(final String reqData1, final String reqData2) {
        if (favouriteController.isFavourite(reqData1, reqData2)) {
            setAsFavouriteIcon(true);
        } else {
            setAsFavouriteIcon(false);
        }
        return this.menu;
    }

    public void setAsFavouriteIcon(final boolean b) {
        this.favItem.setVisible(b);
        this.notFavItem.setVisible(!b);
    }

    public void setAllEnabled(final boolean b) {
        this.favItem.setVisible(b);
        this.notFavItem.setVisible(b);
    }

    public Menu onOptionsItemSelected(final MenuItem item, final String[] details, final Activity activity) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            activity.finish();
        } else if (id == R.id.action_prefere) {
            try {
                Toast.makeText(context, "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();
                addFavourite(details);
                setAsFavouriteIcon(true);
            } catch (FavouriteException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.action_deprefere) {
            Toast.makeText(context, "Rimosso dai preferiti", Toast.LENGTH_SHORT).show();
            removeFavourite(details);
            setAsFavouriteIcon(false);
        }
        return this.menu;
    }

    private void removeFavourite(final String[] details) {
        favouriteController.removeFavourite(details[0], details[1]);
    }

    private void addFavourite(final String[] details) throws FavouriteException {
        if (details.length == 2) {
            favouriteController.addFavourite(details[0], details[1]);
        } else if (details.length == 4) {
            favouriteController.addFavourite(details[0], details[1], details[2], details[3]);
        }
    }
}
