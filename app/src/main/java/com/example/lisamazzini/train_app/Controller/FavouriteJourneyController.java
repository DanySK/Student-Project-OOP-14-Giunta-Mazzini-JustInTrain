package com.example.lisamazzini.train_app.Controller;


import android.content.Context;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyAdder;

import java.util.ArrayList;
import java.util.List;

public class FavouriteJourneyController {

    private final FavouriteJourneyAdder favouriteJourneyAdder = FavouriteJourneyAdder.getInstance();
    private final Context context;

    public FavouriteJourneyController(Context context) {
        this.context = context;
        favouriteJourneyAdder.setContext(this.context);
    }

    public void setAsFavourite(String departure, String arrival) {

        favouriteJourneyAdder.addFavourite(departure, arrival);
    }

    public List<String> getFavourites() {
        return new ArrayList<String>(favouriteJourneyAdder.getFavourites().keySet());
    }
}
