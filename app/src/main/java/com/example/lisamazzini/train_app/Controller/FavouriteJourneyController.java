package com.example.lisamazzini.train_app.Controller;


import android.content.Context;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyAdder;

import java.util.ArrayList;
import java.util.List;

public class FavouriteJourneyController {

    private final FavouriteJourneyAdder favouriteJourneyAdder = FavouriteJourneyAdder.getInstance();

    public FavouriteJourneyController(Context context) {
        favouriteJourneyAdder.setContext(context);
    }

    public void setAsFavourite(String departure, String arrival) {
        favouriteJourneyAdder.addFavourite(departure, arrival);
    }

    public void removeAll() {
        favouriteJourneyAdder.removeFavourites();
    }

    public List<String> getFavourites() {
        return new ArrayList<String>(favouriteJourneyAdder.getFavourites().keySet());
    }
}
