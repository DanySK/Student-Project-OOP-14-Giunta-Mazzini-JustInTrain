package com.example.lisamazzini.train_app.gui.fragment;

import com.octo.android.robospice.SpiceManager;

/**
 * Interfaccia che modella il comportamento dei Fragment che necessitano di
 * connessione a Internet e che quindi dovranno gestire uno SpiceManager.
 *
 * @author albertogiunta
 */

public interface IRobospiceFragment extends IBaseFragment {

    /**
     * Metodo che resetta lo spiceManager e lo fa ripartire.
     */
    void resetRequests();

    /**
     * Setter per lo spiceManager.
     * @param pSpiceManager spiceManager
     */
    void setSpiceManager(final SpiceManager pSpiceManager);

    /**
     * Getter per lo spiceManager.
     * @return spiceManager
     */
    SpiceManager getSpiceManager();

}
