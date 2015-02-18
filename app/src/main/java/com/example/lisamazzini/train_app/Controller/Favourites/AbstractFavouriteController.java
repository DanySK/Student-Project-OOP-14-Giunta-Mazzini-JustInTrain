package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.FavouriteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Classe astratta che implementa l'interfaccia IFavouriteController e quindi modella parzialmente il concetto di preferito
 * Di norma potrebbe cambiare solo la modalità di aggiunta
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public abstract class AbstractFavouriteController implements IFavouriteController {

    protected SharedPreferences sharedPref;
    protected SharedPreferences.Editor editor;

    @Override
    public abstract void setContext(Context context);

    @Override
    public void addFavourite(final String... strings) throws FavouriteException {
        check();
        if(!alreadyFavourite(buildKey(strings))) {
            editor.putString(buildKey(strings), "");
            editor.apply();
        }else{
            throw new FavouriteException();
        }
    }

    @Override
    public void removeFavourite(final String... data) {
        check();
        editor.remove(buildKey(data));
        editor.apply();
    }

    public void removeFavourites() {
        check();
        for (String s : this.getFavouritesAsList()) {
            editor.remove(s);
        }
        editor.apply();
    }

    @Override
    public Map<String, ?> getFavouritesAsMap() {
        check();
        return sharedPref.getAll();
    }

    @Override
    public List<String> getFavouritesAsList() {
        return new ArrayList<>(getFavouritesAsMap().keySet());
    }

    public boolean isFavourite(final String... strings) {
        return alreadyFavourite(buildKey(strings));
    }

    protected void check() {
        if (sharedPref == null) {
            throw new UnsupportedOperationException("Set your context first");
        }
    }

    protected boolean alreadyFavourite(final String string){
        return getFavouritesAsMap().containsKey(string);
    }

    protected abstract String buildKey(final String... strings);

}
