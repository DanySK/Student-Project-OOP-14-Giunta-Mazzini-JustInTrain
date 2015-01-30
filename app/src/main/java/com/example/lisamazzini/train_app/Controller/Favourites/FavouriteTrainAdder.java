package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by lisamazzini on 28/12/14.
 */

//SINGLETON

public class FavouriteTrainAdder {

    //Name of the file in which the favourites will be saved
    public final static String TRAIN_PREF_FILE = "TrainPref";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    //The singleton instance
    private static FavouriteTrainAdder adder;

    public static FavouriteTrainAdder getInstance(){
        synchronized (FavouriteTrainAdder.class){
            if(adder == null){
                adder = new FavouriteTrainAdder();
            }
            return adder;
        }
    }

    public void setContext(Context context){
        //??????
        sharedPref = context.getSharedPreferences(FavouriteTrainAdder.TRAIN_PREF_FILE, Context.MODE_APPEND);
        editor = sharedPref.edit();
    }

    /**
     * This method add a train number to favourites
     * @param trainNumber
     */
    public void addFavourite(String ... trainNumber){
        check();
        //Nuovo concetto di preferito?
        //createKey(trainNumber[0], trainNumber[1], trainNumber[2]);

        //editor.putString(createKey(trainNumber[0], trainNumber[1], trainNumber[2]), trainNumber[0]);
        editor.putString(trainNumber[0], trainNumber[0]);
        editor.apply();
    }

    /**
     * This method is not forevah, just for da debug
     * @param name
     * @return
     */
    public String getFavourite(String name){
        check();
        return sharedPref.getString(name, "none");
    }

    /**
     * This method removes a train from the favourite
     * @param
     */

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
        StringBuilder builder = new StringBuilder();
        String key = builder.append(trainNumber)
                .append("_")
                .append(departureStation)
                .append("_")
                .append(arrivalStation)
                .toString();

        return key;
    }


}