package com.example.lisamazzini.train_app.controller.favourites;


import android.content.Context;

import com.example.lisamazzini.train_app.model.Constants;

/**
 * Classe che modella un controller per le tratte favorite.
 * Ridefinisce l'aggiunta della tratta salvando come chiave la coppia di id e come valore la coppia di nomi di stazione
 *
 * @author albertogiunta
 */
public class FavouriteJourneyController extends AbstractFavouriteController{

    private static final FavouriteJourneyController ADDER = new FavouriteJourneyController();

    private FavouriteJourneyController() {}

    public static IFavouriteController getInstance() {
        return ADDER;
    }

    @Override
    public void setContext(final Context context) {
        super.sharedPref = context.getSharedPreferences(Constants.JOURNEY_PREF_FILE, Context.MODE_APPEND);
        super.editor = sharedPref.edit();
        editor.apply();
    }

    @Override
    public void addFavourite(final String... strings) {
        super.check();
        String key = buildKey(strings[0], strings[1]);
        if(!super.alreadyFavourite(key)) {
            editor.putString(key, buildKey(strings[2], strings[3]));
            editor.apply();
        }
    }

    protected String buildKey(final String... strings) {
        String finalString = "";
        for (String s : strings) {
            finalString = finalString.concat(s).concat(Constants.SEPARATOR);
        }
        return finalString;
    }
}
