package com.example.lisamazzini.train_app.Older;

import android.content.Context;

import java.util.Map;
import java.util.Set;

/**
 * Created by lisamazzini on 30/12/14.
 */
public interface IFavouriteAdder {

    public void setContext(Context context);

    public void addFavourite(String ... value);

    public String getFavourite(String key);

    public void removeFavourite(String ... key);

    public Map<String, ?> getFavourites();

}
