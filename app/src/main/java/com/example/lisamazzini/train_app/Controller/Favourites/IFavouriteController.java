package com.example.lisamazzini.train_app.controller.favourites;

import android.content.Context;
import com.example.lisamazzini.train_app.exceptions.FavouriteException;
import java.util.Map;

/**
 * Interfaccia che modella il concetto controller dei preferiti con metodi per aggiungere, togliere e restituire i preferiti.
 *
 * @author albertogiunta
 * @author lisamazzini
 */
 public interface IFavouriteController {

    /**
     * Setter per il context.
     * @param context necessario a svolgere qualsiasi operazione sui preferiti
     */
     void setContext(Context context);

    /**
     * Aggiunge un preferito.
     *
     * @param data sono le stringhe che combinate identificano la chiave per ottenere il preferito
     * @throws FavouriteException
     */
     void addFavourite(String... data) throws FavouriteException;

    /**
     * Rimuove un preferito.
     *
     * @param data sono le stringhe che combinate identificano la chiave per rimuovere il preferito
     */
     void removeFavourite(String... data);


    /**
     * Rimuove tutti i preferiti.
     */
     void removeFavourites();

    /**
     * Restituisce tutti i preferiti come mappa chiave - valore.
     * @return la mappa con i preferiti
     */
     Map<String, ?> getFavouritesAsMap();

    /**
     * Metodo per sapere se la combinazione di certi dati sono già presenti tra i preferiti.
     *
     * @param data sono le stringhe che combinate identificano la chiave per ottenere il preferito
     * @return boolean: se l'oggetto è attualmente tra i preferiti o meno
     */
     boolean isFavourite(String... data);

}
