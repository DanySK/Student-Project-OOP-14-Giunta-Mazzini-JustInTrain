package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;

import com.example.lisamazzini.train_app.Exceptions.FavouriteException;

import java.util.List;
import java.util.Map;

/**
 * Interfaccia che modella il concetto controller dei preferiti con metodi per aggiungere, togliere e restituire i preferiti
 *
 * @author albertogiunta
 */
public interface IFavouriteController {

    /**
     * Setter per il context
     * @param context necessario a svolgere qualsiasi operazione sui preferiti
     */
    public abstract void setContext(Context context);

    /**
     * Aggiunge un preferito
     *
     * @param data: sono le stringhe che combinate identificano la chiave per ottenere il preferito
     * @throws FavouriteException
     */
    public void addFavourite(String... data) throws FavouriteException;

    /**
     * Rimuove un preverito
     *
     * @param data: sono le stringhe che combinate identificano la chiave per rimuovere il preferito
     */
    public void removeFavourite(String... data);


    /**
     * Rimuove tutti i preferiti
     */
    public void removeFavourites();

    /**
     * Restituisce tutti i preferiti come mappa chiave - valore
     * @return la mappa con i preferiti
     */
    public Map<String, ?> getFavouritesAsMap();

    /**
     * Restituisce i preferiti come lista di valori (solo le chiavi)
     * @return il keyset della mappa dei preferiti
     */
    public List<String> getFavouritesAsList();


    /**
     * Utile per sapere se la combinazione di certi dati sono già presenti tra i preferiti
     *
     * @param data: sono le stringhe che combinate identificano la chiave per ottenere il preferito
     * @return boolean: se l'oggetto è attualmente tra i preferiti o meno
     */
    public boolean isFavourite(String... data);

}
