package com.example.lisamazzini.train_app.Older;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertogiunta on 17/01/15.
 */
public class ListJourney extends ArrayList<Journey> {

    List<Journey> jpj;
    int radio;

    public ListJourney (List<Journey> l, int radio) {

        this.radio = radio;
        this.jpj = l;
    }

    public List<Journey> getList() {

        return this.jpj;
    }

    public int getRadio() {
        return this.radio;
    }


}
