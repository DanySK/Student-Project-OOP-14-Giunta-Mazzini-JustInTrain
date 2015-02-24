package com.example.lisamazzini.train_app.controller.favourites;

/**
 * Interfaccia che definisce uno strategy per instanziare un determinato FavouriteController.
 * @author albertogiunta
 */
public interface FavouriteControllerStrategy {

    /**
     * Getter per il controller.
     * @return IFavouriteController
     */
    IFavouriteController getController();
}
