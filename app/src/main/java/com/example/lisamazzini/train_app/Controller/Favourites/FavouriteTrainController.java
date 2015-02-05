package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;

import com.example.lisamazzini.train_app.Model.Constants;

public class FavouriteTrainController extends AbstractFavouriteController {

    private static final FavouriteTrainController ADDER = new FavouriteTrainController();

    private FavouriteTrainController() {}

    // TODO però così non è obbligatorio, quindi come fare?
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
        // TODO
        return null;
    }
}
