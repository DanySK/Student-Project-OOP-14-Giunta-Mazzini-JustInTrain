package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Controller.TotalRequests.TrainRequest;
import com.example.lisamazzini.train_app.Model.Constants;

import java.util.*;

/**
 * Classe che funge da controller per il FavouriteTrainListFragment
 */
public class FavouriteTrainListController {

    private final Iterator<String> iterator;

    public FavouriteTrainListController(Map<String, String> map){
        this.iterator = map.keySet().iterator();
    }

    /**
     * Metodo che stabilisce se ci sono altri preferiti
     * @return
     */
    public boolean hasAnotherFavourite(){
        return this.iterator.hasNext();
    }

    /**
     * Metodo che restituisce il preferito successivo
     * @return il preferito
     */
    private String getFavourite(){
        return iterator.next();
    }

    /**
     *
     * @return
     */
    public TrainRequest getRequest() {
        String[] favData = getFavourite().split(Constants.SEPARATOR);
        return new TrainRequest(favData[0], favData[1]);
    }
}
