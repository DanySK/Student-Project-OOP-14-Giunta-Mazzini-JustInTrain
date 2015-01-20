package com.example.lisamazzini.train_app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by albertogiunta on 30/12/14.
 */
public class JourneyFavouriteAdder {

    public final static String JOURNEY_PREF_FILE = "JourneyPref";
    private static JourneyFavouriteAdder adder;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static JourneyFavouriteAdder getInstance() {
        synchronized (JourneyFavouriteAdder.class) {
            if (adder == null) {
                adder = new JourneyFavouriteAdder();
            }
            return adder;
        }
    }

    public void setContext(Context context) {
        sharedPref = context.getSharedPreferences(JOURNEY_PREF_FILE, Context.MODE_APPEND);
        editor = sharedPref.edit();
    }

    public void addFavourite(String ... journeyStations) {
        check();
        Set<String> s = new HashSet<>();
        for (String str : journeyStations) {
            s.add(str);
        }
        editor.putStringSet(journeyStations[0], s);
        editor.apply();
    }

    /*public String getFavourite(String ... journeyStations) {
        check();
    }*/

    public void removeFavourite(String key) {
        check();
        editor.remove(key);
        editor.apply();
    }

    public Map<String, ?> getFavourites() {
        check();
        return sharedPref.getAll();
    }

    private void check() {
        if (sharedPref == null) {
            throw new UnsupportedOperationException("u betta setContext u son of a bitch");
        }
    }

}
