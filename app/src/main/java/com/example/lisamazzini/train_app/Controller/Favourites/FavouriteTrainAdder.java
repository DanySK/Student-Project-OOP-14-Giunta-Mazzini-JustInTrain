package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lisamazzini.train_app.Model.Constants;

import java.util.Map;

public class FavouriteTrainAdder {

    private static FavouriteTrainAdder adder = new FavouriteTrainAdder();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static FavouriteTrainAdder getInstance(){
            return adder;
    }

    public void setContext(Context context){
        sharedPref = context.getSharedPreferences(Constants.TRAIN_PREF_FILE, Context.MODE_APPEND);
        editor = sharedPref.edit();
    }

    public void addFavourite(String ... trainNumber){
        check();
        editor.putString(trainNumber[0], trainNumber[0]);
        editor.apply();
    }

    public String getFavourite(String name){
        check();
        return sharedPref.getString(name, "none");
    }

    public void removeFavourite(String ... data) {
        editor.remove(data[0]);
        editor.apply();
    }

    public Map<String, ?> getFavourites(){
        check();
        return sharedPref.getAll();
    }

    private void check(){
        if(sharedPref == null){
            throw new UnsupportedOperationException("You must setContext before using the adder");
        }
    }

    private String createKey(String trainNumber, String departureStation, String arrivalStation){
        return new StringBuilder().append(trainNumber)
                .append("_")
                .append(departureStation)
                .append("_")
                .append(arrivalStation)
                .toString();
    }
}
