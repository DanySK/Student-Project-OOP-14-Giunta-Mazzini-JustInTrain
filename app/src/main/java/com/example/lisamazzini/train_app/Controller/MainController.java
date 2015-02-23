package com.example.lisamazzini.train_app.controller;


import android.content.Context;
import android.util.Log;

import com.example.lisamazzini.train_app.controller.favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.model.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainController {

    private final IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();

    private final List<String> favouriteStationNames = new LinkedList<>();
    private final List<String> favouriteStationIDs = new LinkedList<>();
    private final List<String> actualJourneyIds = new ArrayList<>();

    /**
     * Costruttore.
     * @param context per le sharedpreferences
     */
    public MainController(final Context context) {
       favouriteJourneyController.setContext(context);
    }


    public final void setCurrentJourney(final int position) {
        actualJourneyIds.clear();
        Collections.addAll(actualJourneyIds, favouriteStationIDs.get(position).split(Constants.SEPARATOR));
    }

    /**
     * Metodo per rimuovere un preferito.
     */
    public final void removeFavourite() {
        favouriteJourneyController.removeFavourite(getActualDepartureId(), getActualArrivalId());
    }

    /**
     * Getter per la lista delle stazioni preferiti.
     * @return lista delle stazioni preferite
     */
    public final List<String> getFavouriteStationNames() {
        return new LinkedList<>(favouriteStationNames);
    }

    /**
     * Getter per l'actualDepartureId.
     * @return actualDepartureId
     */
    public final String getActualDepartureId() {
        return actualJourneyIds.get(0);
    }

    /**
     * Getter per l'actualArrivalId.
     * @return actualArrivalId
     */
    public final String getActualArrivalId() {
        return actualJourneyIds.get(1);
    }

    /**
     * Metodo che aggiorna le liste.
     */
    public final void refreshLists() {
        favouriteStationIDs.clear();
        favouriteStationNames.clear();

        final Map<String, String> favouriteJourneysMap = (Map<String, String>) favouriteJourneyController.getFavouritesAsMap();
        for (final String s : favouriteJourneysMap.keySet()) {
            favouriteStationIDs.add(s);
        }

        for (final String s : favouriteStationIDs) {
            favouriteStationNames.add(favouriteJourneysMap.get(s));
        }
    }

    /**
     * Metodo che controlla se sono presenti preferiti.
     * @return true se sono presenti, false altrimenti
     */
    public final boolean isPresentAnyFavourite() {
        return !favouriteStationIDs.isEmpty();
    }
}
