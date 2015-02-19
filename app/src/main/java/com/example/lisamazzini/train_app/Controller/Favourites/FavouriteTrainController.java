package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;

import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.Model.Constants;

/**
 * Classe che modella un controller per i treni favoriti.
 *
 * @author lisamazzini
 */
public class FavouriteTrainController extends AbstractFavouriteController {

    private final static FavouriteTrainController ADDER = new FavouriteTrainController();

    private FavouriteTrainController() {}

    public static IFavouriteController getInstance() {
        return ADDER;
    }

    @Override
    public void addFavourite(String... strings) throws FavouriteException {
        check();
        if(!alreadyFavourite(buildKey(strings))) {
            editor.putString(buildKey(strings), "");
            editor.apply();
        }else{
            throw new FavouriteException();
        }
    }

    public void setContext(final Context context){
        super.sharedPref = context.getSharedPreferences(Constants.TRAIN_PREF_FILE, Context.MODE_APPEND);
        super.editor = sharedPref.edit();
        editor.apply();
    }

    @Override
    protected String buildKey(final String... strings) {
        return strings[0] + Constants.SEPARATOR + strings[1];
    }
}
