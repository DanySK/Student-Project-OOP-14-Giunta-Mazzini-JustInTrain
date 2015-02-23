package com.example.lisamazzini.train_app.controller.favourites;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Classe astratta che implementa l'interfaccia IFavouriteController e quindi modella parzialmente il concetto di preferito.
 * Di norma potrebbe cambiare solo la modalità di aggiunta.
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public abstract class AbstractFavouriteController implements IFavouriteController {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;



    @Override
    public final void removeFavourite(final String... data) {
        check();
        editor.remove(buildKey(data));
        editor.apply();
    }

    @Override
    public final void removeFavourites() {
        check();
        for (final String s : this.getFavouritesAsList()) {
            editor.remove(s);
        }
        editor.apply();
    }

    @Override
    public final  Map<String, ?> getFavouritesAsMap() {
        check();
        return sharedPref.getAll();
    }

    @Override
    public final boolean isFavourite(final String... strings) {
        return alreadyFavourite(buildKey(strings));
    }

    /**
     * Getter per le SharedPreferences.
     * @return sharedPreferences
     */
    public final SharedPreferences getSharedPref() {
        return sharedPref;
    }

    /**
     * Setter per le SharedPreferences.
     * @param pSharedPref da settare
     */
    public final void setSharedPref(final SharedPreferences pSharedPref) {
        this.sharedPref = pSharedPref;
    }

    /**
     * Getter per l'Editor.
     * @return editor
     */
    public final SharedPreferences.Editor getEditor() {
        return editor;
    }
    /**
     * Setter per l'Editor.
     * @param pEditor da settare
     */
    public final void setEditor(final SharedPreferences.Editor pEditor) {
        this.editor = pEditor;
    }
    @Override
    public abstract void addFavourite(final String... strings);

    @Override
    public abstract void setContext(Context context);

    /**
     * Metodo che controlla che le SharedPreferences siano state inizializzate.
     */
    protected final void check() {
        if (sharedPref == null) {
            throw new UnsupportedOperationException("Set your context first");
        }
    }

    /**
     * Metodo che controlla se un preferito è già salvato.
     * @param string preferito
     * @return true se è già preferito, false altrimenti
     */
    protected final boolean alreadyFavourite(final String string) {
        return getFavouritesAsMap().containsKey(string);
    }

    /**
     * Metodo che costruisce la chiave da salvare nelle SharedPreferences.
     * @param strings che descrivono il preferito
     * @return la key
     */
    protected abstract String buildKey(final String... strings);

    /**
     * Getter per la lista di key della mappa dei preferiti.
     * @return lista di key
     */
    private List<String> getFavouritesAsList() {
        return new ArrayList<>(getFavouritesAsMap().keySet());
    }
}
