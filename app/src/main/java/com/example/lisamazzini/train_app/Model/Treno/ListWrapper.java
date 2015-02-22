package com.example.lisamazzini.train_app.model.treno;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe che fa da wrapper per una lista di String.
 *
 * @author lisamazzini
 */
public class ListWrapper extends LinkedList<String> {
    private final List<String> list;

    /**
     * Costruttore che prende in input una list<String>.
     * @param pList lista di string
     */
    public ListWrapper(final List<String> pList) {
        this.list = pList;
    }

    /**
     * Metodo che ritorna la lista wrappata in forma di List<String>.
     * @return la lista wrappata.
     */
    public final List<String> getList() {
        return this.list;
    }
}
