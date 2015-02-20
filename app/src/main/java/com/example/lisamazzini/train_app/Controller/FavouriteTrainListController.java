package com.example.lisamazzini.train_app.controller;

import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.network.total.TrainRequest;
import com.example.lisamazzini.train_app.model.Constants;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Classe che funge da controller per il FavouriteTrainListFragment.
 *
 * @author lisamazzini
 */
public class FavouriteTrainListController {

    private final Iterator<String> iterator;

    private final List<Treno> favouriteTrainsList = new LinkedList<>();

    /**
     * Costruttore.
     * @param map mappa dei preferiti
     */
    public FavouriteTrainListController(final Map<String, String> map) {
        this.iterator = map.keySet().iterator();
    }

    /**
     * Metodo che stabilisce se ci sono altri preferiti.
     * @return true se ha un altro preferito, false altrimenti
     */
    public final boolean hasAnotherFavourite() {
        return this.iterator.hasNext();
    }

    /**
     * Metodo che restituisce la lista dei treni preferiti.
     * @return lista dei treni
     */
    public final List<Treno> getFavouriteTrainsList() {
        return favouriteTrainsList;
    }

    /**
     * Metodo che aggiunge un treno alla lista dei preferiti.
     * @param train da aggiungere
     */
    public final void addToFavouriteTrainList(final Treno train) {
        favouriteTrainsList.add(train);
    }
    /**
     * Metodo che restituisce il preferito successivo.
     * @return il preferito
     */
    private String getFavourite() {
        return iterator.next();
    }

    /**
     * Metodo che fa la richiesta a partire dal numero e id stazione di origine del treno.
     * @return TrainRequest
     */
    public final TrainRequest getRequest() {
        final String[] favData = getFavourite().split(Constants.SEPARATOR);
        return new TrainRequest(favData[0], favData[1]);
    }
}
