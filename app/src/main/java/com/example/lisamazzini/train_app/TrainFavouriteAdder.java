package com.example.lisamazzini.train_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

/**
 * Created by lisamazzini on 28/12/14.
 */

//SINGLETON

public class TrainFavouriteAdder implements IFavouriteAdder{

    //Name of the file in which the favourites will be saved
    public final static String TRAIN_PREF_FILE = "TrainPref";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    //The singleton instance
    private static TrainFavouriteAdder adder;

    public static TrainFavouriteAdder getInstance(){
        synchronized (TrainFavouriteAdder.class){
            if(adder == null){
                adder = new TrainFavouriteAdder();
            }
            return adder;
        }
    }

    public void setContext(Context context){
        //??????
        sharedPref = context.getSharedPreferences(TrainFavouriteAdder.TRAIN_PREF_FILE, Context.MODE_APPEND);
        editor = sharedPref.edit();
    }

    /**
     * This method add a train number to favourites
     * @param trainNumber
     */
    public void addFavourite(String ... trainNumber){
        check();
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
     * @param key
     */
    @Override
    public void removeFavourite(String key) {
        check();
        editor.remove(key);
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


}