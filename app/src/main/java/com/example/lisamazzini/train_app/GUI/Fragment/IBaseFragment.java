package com.example.lisamazzini.train_app.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Interfaccia che modella un fragment e che deve essere implementata da tutti i fragment.
 *
 * @author albertogiunta
 */
public interface IBaseFragment {

    /**
     * Metodo che viene eseguito alla creazione del fragment.
     * @param inflater layoutinflater
     * @param container container
     * @param savedInstanceState bundle
     * @return view
     */
    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
