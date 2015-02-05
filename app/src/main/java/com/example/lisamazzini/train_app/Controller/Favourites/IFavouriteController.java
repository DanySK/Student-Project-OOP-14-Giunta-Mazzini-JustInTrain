package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;

import java.util.List;
import java.util.Map;

public interface IFavouriteController {

    public abstract void setContext(Context context);

    public void addFavourite(String... stations);

    public void removeFavourite(String... data);

    public void removeFavourites();

    public Map<String, ?> getFavouritesAsMap();

    public List<String> getFavouritesAsList();

}
