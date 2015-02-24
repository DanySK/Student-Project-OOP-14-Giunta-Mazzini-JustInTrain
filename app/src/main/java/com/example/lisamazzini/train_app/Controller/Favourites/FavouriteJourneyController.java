package com.example.lisamazzini.train_app.controller.favourites;


import android.content.Context;

import com.example.lisamazzini.train_app.model.Constants;

/**
 * Classe che modella un controller per le tratte favorite.
 * Ridefinisce l'aggiunta della tratta salvando come chiave la coppia di id e come valore la coppia di nomi di stazione
 *
 * @author albertogiunta
 */
public final class FavouriteJourneyController extends AbstractFavouriteController {

    private static final FavouriteJourneyController ADDER = new FavouriteJourneyController();

    /**
     * Metodo che ritorna un'istanza del controller.
     * @return IFavouriteController
     */
    public static IFavouriteController getInstance() {
        return ADDER;
    }

    private FavouriteJourneyController() { }

    @Override
    public void setContext(final Context context) {
        setSharedPref(context.getSharedPreferences(Constants.JOURNEY_PREF_FILE, Context.MODE_APPEND));
        setEditor(getSharedPref().edit());
        getEditor().apply();
    }

    @Override
    public void addFavourite(final String... strings) {
        super.check();
        final String key = buildKey(strings[0], strings[1]);
        if (!super.alreadyFavourite(key)) {
            getEditor().putString(key, strings[2]);
            getEditor().apply();
        }
    }

    @Override
    protected String buildKey(final String... strings) {
        String finalString = "";
        for (final String s : strings) {
            finalString = finalString.concat(s).concat(Constants.SEPARATOR);
        }
        return finalString;
    }
}
