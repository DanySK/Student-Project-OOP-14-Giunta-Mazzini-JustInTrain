package com.example.lisamazzini.train_app.model.tragitto;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modella un oggetto Tragitto, necessaria per il parsing json.
 * Ha una lista di soluzioni.
 *
 * @author albertogiunta
 */
public class Tragitto {

    private List<Soluzioni> soluzioni = new ArrayList<Soluzioni>();

    /**
     * Getter per la lista di Soluzioni.
     * @return la lista di Soluzioni
     */
    public final List<Soluzioni> getSoluzioni() {
        return soluzioni;
    }

    /**
     * Setter per la lista di soluzioni.
     * @param pSoluzioni lista da settare
     */
    public final void setSoluzioni(final List<Soluzioni> pSoluzioni) {
        this.soluzioni = pSoluzioni;
    }
}