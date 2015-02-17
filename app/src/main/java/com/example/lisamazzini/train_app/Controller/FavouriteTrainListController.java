package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.Treno.Treno;
import com.example.lisamazzini.train_app.Network.TotalRequests.TrainRequest;
import com.example.lisamazzini.train_app.Model.Constants;

import java.util.*;

/**
 * Classe che funge da controller per il FavouriteTrainListFragment
 *
 * @author lisamazzini
 */
public class FavouriteTrainListController {

    private final Iterator<String> iterator;


    private List<Treno> favouriteTrainsList = new LinkedList<>();

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
     *
     * @return
     */
    public List<Treno> getFavouriteTrainsList() {
        return favouriteTrainsList;
    }

    /**
     *
     * @param train
     */
    public void addToFavouriteTrainList(Treno train){
        favouriteTrainsList.add(train);
    }
    /**
     * Metodo che restituisce il preferito successivo
     * @return il preferito
     */
    private String getFavourite(){
        return iterator.next();
    }

    /**
     *Meotodo che fa la richiesta a partire dal numero e id stazione di origine del treno.
     * @return TrainRequest
     */
    public TrainRequest getRequest() {
        String[] favData = getFavourite().split(Constants.SEPARATOR);
        return new TrainRequest(favData[0], favData[1]);
    }
}
