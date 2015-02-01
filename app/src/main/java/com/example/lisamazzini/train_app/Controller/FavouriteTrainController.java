package com.example.lisamazzini.train_app.Controller;

import android.content.Context;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.Favourites.FavouriteTrainAdder;

import java.util.Map;

public class FavouriteTrainController {

    private FavouriteTrainAdder favouriteAdder = FavouriteTrainAdder.getInstance();
    private final Context context;

    public FavouriteTrainController(Context context){
        favouriteAdder.setContext(context);
        this.context = context;
    }

    public void addFavourite(String trainNumber){
        favouriteAdder.addFavourite(trainNumber);
        Toast.makeText(context,  "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();
    }

    public void removeFavourite(String trainNumber){
        favouriteAdder.removeFavourite(trainNumber);
        Toast.makeText(context,  "Rimosso dai preferiti", Toast.LENGTH_SHORT).show();

    }

    public Map<String, String> getMap(){
        return (Map <String, String>) favouriteAdder.getFavourites();
    }
}
