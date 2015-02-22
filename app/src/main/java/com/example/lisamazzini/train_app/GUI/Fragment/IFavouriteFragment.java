package com.example.lisamazzini.train_app.gui.fragment;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.lisamazzini.train_app.controller.favourites.FavouriteControllerStrategy;

/**
 * Interfaccia che modella un fragment che gestisce i preferiti, deve essere implementata dai fragment che prevedono questa gestione.
 *
 * @author albertogiunta
 */
public interface IFavouriteFragment extends IBaseFragment {

    /**
     * Metodo eseguito prima di qualsiasi altro alla creazione del fragment, serve a implementare il metodo setHasOptionsMenu(true).
     * @param savedInstanceState bundle
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * Metodo eseguito alla creazione della toolbar che serve a inflatarla.
     * @param menu menu
     * @param inflater inflater
     */
    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    /**
     * Metodo eseguito quando viene selezionato un elemento della toolbar e di conseguenze esegue delle operazioni.
     * @param item l'elemento selezionato
     * @return boolean
     */
    boolean onOptionsItemSelected(MenuItem item);

    /**
     * Metodo che viene chiamato per settare il menu dipendentemente dall'activity in cui si trova.
     * @param menu menu
     */
    void setMenu(final Menu menu);

    /**
     * Metodo che viene chiamato per settare l'icona a favorito o meno.
     * @param reqData1 dati utili a verificare se è preferito
     * @param reqData2 dati utili a verificare se è preferito
     */
    void toggleFavouriteIcon(final String reqData1, final String reqData2);

    /**
     * Metodo che viene chiamato per cambiare l'icona.
     * @param b booleano per settare l'icona a favorito o meno
     */
    void setAsFavouriteIcon(final boolean b);

    /**
     * Metodo che viene chiamato per abilitare o disabilitare tutte le icone.
     * @param b boolean
     */
    void setAllEnabled(final boolean b);

    /**
     * Metodo che viene chiamato per settare il favouriteController in base al quale fare le elaborazioni successivo.
     * @param strategy strategy utile a settare il favouriteController a seconda di chi lo implementa
     */
    void setFavouriteController(final FavouriteControllerStrategy strategy);
}