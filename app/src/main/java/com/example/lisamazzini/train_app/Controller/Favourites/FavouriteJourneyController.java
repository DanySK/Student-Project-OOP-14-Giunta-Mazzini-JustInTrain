package com.example.lisamazzini.train_app.Controller.Favourites;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.Model.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe che modella un controller per le tratte favorite.
 * Ridefinisce l'aggiunta della tratta salvando come chiave la coppia di id e come valore la coppia di nomi di stazione
 */
public class FavouriteJourneyController extends AbstractFavouriteController{

    private static final FavouriteJourneyController ADDER = new FavouriteJourneyController();

    private FavouriteJourneyController() {}

    public static IFavouriteController getInstance() {
        return ADDER;
    }

    @Override
    public void setContext(Context context) {
        super.sharedPref = context.getSharedPreferences(Constants.JOURNEY_PREF_FILE, Context.MODE_APPEND);
        super.editor = sharedPref.edit();
        editor.apply();
    }

    @Override
    public void addFavourite(String... strings) throws FavouriteException {
        super.check();
        String key = buildKey(strings[0], strings[1]);
        if(!super.alreadyFavourite(key)) {
            editor.putString(key, buildKey(strings[2], strings[3]));
            editor.apply();
        }else{
            throw new FavouriteException();
        }
    }

    protected String buildKey(String... strings) {
        String finalString = "";
        for (String s : strings) {
            finalString = finalString.concat(s).concat(Constants.SEPARATOR);
        }
        return finalString;
    }
}
