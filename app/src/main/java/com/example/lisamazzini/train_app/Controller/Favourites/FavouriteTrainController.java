package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;

import com.example.lisamazzini.train_app.Model.Constants;

public class FavouriteTrainController extends AbstractFavouriteController {

    private final static FavouriteTrainController ADDER = new FavouriteTrainController();

    private FavouriteTrainController() {}

    public static IFavouriteController getInstance() {
        return ADDER;
    }

    public void setContext(Context context){
        super.sharedPref = context.getSharedPreferences(Constants.TRAIN_PREF_FILE, Context.MODE_APPEND);
        super.editor = sharedPref.edit();
        editor.apply();
    }

    @Override
    protected String buildKey(String... strings) {
        return strings[0] + Constants.SEPARATOR + strings[1];
    }
}
