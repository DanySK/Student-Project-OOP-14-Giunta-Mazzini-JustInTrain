package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Controller.Favourites.IFavouriteController;
import com.example.lisamazzini.train_app.Exceptions.FavouriteException;
import com.example.lisamazzini.train_app.Exceptions.WrongFavouriteInputException;
import com.example.lisamazzini.train_app.Model.Constants;

public abstract class AbstractFavouriteController {

//    protected IFavouriteController favouriteController;
//
//    public void removeFavourite(String[] details) {
//        favouriteController.removeFavourite(details[0], details[1]);
//    }
//
//    private void addFavourite(String favouriteType, String[] details) throws FavouriteException {
//        if (favouriteType.equals(Constants.JOURNEY_FAVOURITE)) {
//            if (details.length != 2) {
//                throw new WrongFavouriteInputException();
//            }
//            favouriteController.addFavourite(details[0], details[1]);
//        } else if (favouriteType.equals(Constants.STATION_FAVOURITE)) {
//            if (details.length != 4) {
//                throw new WrongFavouriteInputException();
//            }
//            favouriteController.addFavourite(details[0], details[1], details[2], details[3]);
//        }
//    }
}
