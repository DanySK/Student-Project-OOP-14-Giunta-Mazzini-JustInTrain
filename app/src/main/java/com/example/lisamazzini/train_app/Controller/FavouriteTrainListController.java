package com.example.lisamazzini.train_app.Controller;

import java.util.*;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class FavouriteTrainListController {

    private final Iterator<String> iterator;

    public FavouriteTrainListController(Map<String, String> map){
        this.iterator = map.keySet().iterator();
    }

    public boolean hasAnotherFavourite(){
        return this.iterator.hasNext();
    }

    public String getFavourite(){
        return iterator.next();
    }

    //public TrainDataRequest getRequest(){
       // return new TrainRequest(this.iterator.next());
    //}

}