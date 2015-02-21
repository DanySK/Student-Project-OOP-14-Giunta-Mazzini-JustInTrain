package com.example.lisamazzini.train_app.gui.fragment.pickers;

import android.app.Dialog;
import android.os.Bundle;

/**
 * Interfaccia che modella un date o time picker dialog.
 * @param <X> Parametro relativo al listener da implementare (Date / Time PickerDialog.On Time / Date SetListener
 *
 * @author lisamazzini
 */
public interface IPicker<X> {

    /**
     * Metodo che setta il callback che verrà usato dal dialog.
     * @param pListener il listener che verrà usato
     */
    void setCallback(final X pListener);

    /**
     * Metodo che setta le variabili relative al tempo che vengono passate in input.
     * @param args il bundle contenente i valori
     */
    void setArguments(final Bundle args);

    /**
     * Metodo che istanzia e ritorna il dialog.
     * @param savedInstanceState il bundle con i valori
     * @return il dialog
     */
    Dialog onCreateDialog(Bundle savedInstanceState);

}
