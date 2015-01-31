package com.example.lisamazzini.train_app.Controller;


import android.content.Context;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteJourneyAdder;

public class FavouriteJourneyController {

    private final FavouriteJourneyAdder favouriteJourneyAdder = FavouriteJourneyAdder.getInstance();
    private final Context context;
    private final String departure;
    private final String arrival;

    public FavouriteJourneyController(Context context, String departure, String arrival) {
        this.context = context;
        this.departure = departure;
        this.arrival = arrival;
    }

    public void setAsFavourite() {
        favouriteJourneyAdder.setContext(context);
        favouriteJourneyAdder.addFavourite();
    }
}
