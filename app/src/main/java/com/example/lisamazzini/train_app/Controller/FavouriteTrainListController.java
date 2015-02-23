package com.example.lisamazzini.train_app.controller;

import android.content.Context;

import com.example.lisamazzini.train_app.controller.favourites.FavouriteTrainController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.network.total.TrainRequest;
import com.example.lisamazzini.train_app.model.Constants;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
     * @param context context necessario per ottenere i preferiti
     */
    public FavouriteTrainListController(final Context context) {
        final IFavouriteController favouriteController = FavouriteTrainController.getInstance();
        favouriteController.setContext(context);
        this.iterator = favouriteController.getFavouritesAsMap().keySet().iterator();
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
