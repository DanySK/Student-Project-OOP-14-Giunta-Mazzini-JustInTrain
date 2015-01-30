package com.example.lisamazzini.train_app.Controller.Favourites;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.lisamazzini.train_app.Model.Constants;

import java.util.Map;
import java.util.Set;

public class FavouriteJourneyAdder {

    private final static FavouriteJourneyAdder ADDER = new FavouriteJourneyAdder();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static FavouriteJourneyAdder getInstance() {
        return ADDER;
    }

    public void setContext(Context context) {
        sharedPref = context.getSharedPreferences(Constants.JOURNEY_PREF_FILE, Context.MODE_APPEND);
        editor = sharedPref.edit();
    }

    public void addFavourite() {

        editor.apply();
    }

    public void removeFavourite() {

        editor.apply();
    }
    public Map<String, ?> getFavourites() {

        return sharedPref.getAll();
    }

    private void check() {
        if (sharedPref == null) {
            throw new UnsupportedOperationException("Set your context first");
        }
    }

}
