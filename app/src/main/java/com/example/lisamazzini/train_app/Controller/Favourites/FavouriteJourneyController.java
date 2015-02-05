package com.example.lisamazzini.train_app.Controller.Favourites;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.lisamazzini.train_app.Model.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavouriteJourneyController extends AbstractFavouriteController{

    private static final FavouriteJourneyController ADDER = new FavouriteJourneyController();

    private FavouriteJourneyController() {}

    // TODO però così non è obbligatorio, quindi come fare?
    public static IFavouriteController getInstance() {
        return ADDER;
    }

    @Override
    public void setContext(Context context) {
        super.sharedPref = context.getSharedPreferences(Constants.JOURNEY_PREF_FILE, Context.MODE_APPEND);
        super.editor = sharedPref.edit();
        editor.apply();
    }

    protected String buildKey(String... strings) {
        String finalString = "";
        for (String s : strings) {
            finalString = finalString.concat(s).concat(Constants.SEPARATOR);
        }
        return finalString;
    }
}
