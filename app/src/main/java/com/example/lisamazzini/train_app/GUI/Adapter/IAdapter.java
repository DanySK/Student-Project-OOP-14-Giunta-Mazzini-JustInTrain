package com.example.lisamazzini.train_app.gui.adapter;

import android.view.ViewGroup;

/**
 * Interfaccia che devono implementare tutti gli adapter.
 * @param <X> il tipo di viewholder
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public interface IAdapter<X> {

    /**
     * Metodo che setta la view quando il sistema ne ha bisogno.
     * @param parent il viewgroup genitore
     * @param viewType il tipo di view
     * @return il tipo di viewholder
     */
    X onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * Metodo che setta il contenuto della view di cui sopra.
     * @param holder il tipo di viewholder
     * @param position posizione in lista dell'elemento da inflatare
     */
    void onBindViewHolder(X holder, int position);

    /**
     * Getter del numero di elementi nella lista di origine.
     * @return il numero di elementi
     */
    int getItemCount();

}
