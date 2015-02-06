package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.Constants;

import java.util.*;

public class FavouriteTrainListController {

    private final Iterator<String> iterator;

    public FavouriteTrainListController(Map<String, String> map){
        this.iterator = map.keySet().iterator();
    }

    public boolean hasAnotherFavourite(){
        return this.iterator.hasNext();
    }

    private String getFavourite(){
        return iterator.next();
    }

    public TrainRequest getRequest() {
        String[] favData = getFavourite().split(Constants.SEPARATOR);
        return new TrainRequest(favData[0], favData[1]);
    }
}
