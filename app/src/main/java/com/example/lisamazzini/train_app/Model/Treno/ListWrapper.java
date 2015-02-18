package com.example.lisamazzini.train_app.Model.Treno;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 11/02/15.
 */
public class ListWrapper extends LinkedList<String> {
    private final List<String> list;

    public ListWrapper(final List<String> list) {
        this.list = list;
    }

    public final List<String> getList() {
        return this.list;
    }
}
