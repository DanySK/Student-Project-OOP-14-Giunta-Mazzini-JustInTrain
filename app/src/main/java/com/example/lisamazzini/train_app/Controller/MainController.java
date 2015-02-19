package com.example.lisamazzini.train_app.Controller;


import android.content.Context;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyController;
import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Model.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainController {

    private IFavouriteController favouriteJourneyController = FavouriteJourneyController.getInstance();

    private final List<String> favouriteStationNames = new LinkedList<>();
    private final List<String> favouriteStationIDs = new LinkedList<>();
    private final List<String> actualJourneyIds = new ArrayList<>();

    public MainController(Context context) {
       favouriteJourneyController.setContext(context);
    }

    public void setCurrentJourney(int position) {
        actualJourneyIds.clear();
        Collections.addAll(actualJourneyIds, favouriteStationIDs.get(position).split(Constants.SEPARATOR));
    }

    public void removeFavourite() {
        favouriteJourneyController.removeFavourite(getActualDepartureId(), getActualArrivalId());
    }

    public List<String> getFavouriteStationNames() {
        return new LinkedList<>(favouriteStationNames);
    }

    public String getActualDepartureId() {
        return actualJourneyIds.get(0);
    }

    public String getActualArrivalId() {
        return actualJourneyIds.get(1);
    }


    public void refreshLists() {
        favouriteStationIDs.clear();
        favouriteStationNames.clear();

        Map<String, String> favouriteJourneysMap;
        for (String s : (favouriteJourneysMap = (Map<String, String>) favouriteJourneyController.getFavouritesAsMap()).keySet()) {
            favouriteStationIDs.add(s);
        }

        for (String s : favouriteStationIDs) {
            favouriteStationNames.add(favouriteJourneysMap.get(s).replaceAll(Constants.SEPARATOR, " "));
        }
    }

    public boolean isPresentAnyFavourite() {
        return favouriteStationIDs.size() > 0;
    }
}
