package com.example.lisamazzini.train_app.controller;


import android.content.Context;

import com.example.lisamazzini.train_app.controller.favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;
import com.example.lisamazzini.train_app.model.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainController {

    private IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();

    private final List<String> favouriteStationNames = new LinkedList<>();
    private final List<String> favouriteStationIDs = new LinkedList<>();
    private final List<String> actualJourneyIds = new ArrayList<>();

    public MainController(final Context context) {
       favouriteJourneyController.setContext(context);
    }

    public final void setCurrentJourney(final int position) {
        actualJourneyIds.clear();
        Collections.addAll(actualJourneyIds, favouriteStationIDs.get(position).split(Constants.SEPARATOR));
    }

    public final void removeFavourite() {
        favouriteJourneyController.removeFavourite(getActualDepartureId(), getActualArrivalId());
    }

    public final List<String> getFavouriteStationNames() {
        return new LinkedList<>(favouriteStationNames);
    }

    public final String getActualDepartureId() {
        return actualJourneyIds.get(0);
    }

    public final String getActualArrivalId() {
        return actualJourneyIds.get(1);
    }


    public final void refreshLists() {
        favouriteStationIDs.clear();
        favouriteStationNames.clear();

        Map<String, String> favouriteJourneysMap = (Map<String, String>) favouriteJourneyController.getFavouritesAsMap();
        for (String s : (favouriteJourneysMap.keySet())) {
            favouriteStationIDs.add(s);
        }

        for (String s : favouriteStationIDs) {
            favouriteStationNames.add(favouriteJourneysMap.get(s).replaceAll(Constants.SEPARATOR, " "));
        }
    }

    public final boolean isPresentAnyFavourite() {
        return favouriteStationIDs.size() > 0;
    }
}
