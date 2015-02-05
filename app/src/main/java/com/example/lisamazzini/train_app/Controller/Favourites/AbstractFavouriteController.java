package com.example.lisamazzini.train_app.Controller.Favourites;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractFavouriteController implements IFavouriteController {

    protected SharedPreferences sharedPref;
    protected SharedPreferences.Editor editor;

    @Override
    public abstract void setContext(Context context);

    @Override
    public void addFavourite(String... strings) {
        check();
        editor.putString(buildKey(strings), "");
        editor.apply();
    }

    @Override
    public void removeFavourite(String... data) {
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

    private void check() {
        if (sharedPref == null) {
            throw new UnsupportedOperationException("Set your context first");
        }
    }

    protected abstract String buildKey(String... strings);
}
