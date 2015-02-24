package com.example.lisamazzini.train_app.controller.favourites;

import android.content.Context;

import com.example.lisamazzini.train_app.model.Constants;

/**
 * Classe che modella un controller per i treni favoriti.
 *
 * @author lisamazzini
 */
public final class FavouriteTrainController extends AbstractFavouriteController {

    private static final FavouriteTrainController ADDER = new FavouriteTrainController();

    /**
     * Metodo che restituisce un'istanza del controller.
     * @return IFavouriteController
     */
    public static IFavouriteController getInstance() {
        return ADDER;
    }

    private FavouriteTrainController() { }

    @Override
    public void addFavourite(final String... strings) {
        check();
        getEditor().putString(buildKey(strings), "");
        getEditor().apply();
    }

    @Override
    public void setContext(final Context context) {
        setSharedPref(context.getSharedPreferences(Constants.TRAIN_PREF_FILE, Context.MODE_APPEND));
        setEditor(getSharedPref().edit());
        getEditor().apply();
    }

    @Override
    protected String buildKey(final String... strings) {
        return strings[0] + Constants.SEPARATOR + strings[1];
    }
}
