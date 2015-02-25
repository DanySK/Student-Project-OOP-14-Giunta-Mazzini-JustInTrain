package com.example.lisamazzini.train_app.gui.fragment;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;

/**
 * Fragment astratto che devono estendere tutti i fragment che necessitano di utilizzare robospice.
 *
 * @author albertogiunta
 */
public abstract class AbstractRobospiceFragment extends Fragment implements IRobospiceFragment {

    private SpiceManager spiceManager;

    /**
     * Metodo che fa partire lo spiceManager.
     */
    @Override
    public final void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    /**
     * Metodo che stoppa lo spiceManager dopo aver inviato il comando di non notificare nessun listener.
     */
    @Override
    public final void onStop() {
        spiceManager.dontNotifyAnyRequestListeners();
        spiceManager.shouldStop();
        super.onStop();
    }

    /**
     * Metodo che resetta lo spiceManager e lo fa ripartire.
     */
    @Override
    public final void resetRequests() {
        if (spiceManager.isStarted()) {
            spiceManager.dontNotifyAnyRequestListeners();
            spiceManager.shouldStop();
            spiceManager.start(getActivity());
        }
    }

    /**
     * Setter per lo spiceManager.
     * @param pSpiceManager spiceManager
     */
    @Override
    public final void setSpiceManager(final SpiceManager pSpiceManager) {
        this.spiceManager = pSpiceManager;
    }

    /**
     * Getter per lo spiceManager.
     * @return spiceManager
     */
    @Override
    public final SpiceManager getSpiceManager() {
        return spiceManager;
    }
}