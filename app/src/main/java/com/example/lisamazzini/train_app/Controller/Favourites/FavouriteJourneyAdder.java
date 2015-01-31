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

    private FavouriteJourneyAdder() {}

    public void setContext(Context context) {
        sharedPref = context.getSharedPreferences(Constants.JOURNEY_PREF_FILE, Context.MODE_APPEND);
        editor = sharedPref.edit();
    }

    public void addFavourite(String... stations) {
        check();
        editor.putString(buildKey(stations), "");
        editor.apply();
    }

    public void removeFavourite(String... data) {
        check();
        editor.remove(buildKey(data));
        editor.apply();
    }
    public Map<String, ?> getFavourites() {
        check();
        return sharedPref.getAll();
    }

    private String buildKey(String... strings) {
        String finalString = "";
        for (String s : strings) {
            finalString.concat(s).concat("_");
        }
        return finalString;
    }

    private void check() {
        if (sharedPref == null) {
            throw new UnsupportedOperationException("Set your context first");
        }
    }

}
