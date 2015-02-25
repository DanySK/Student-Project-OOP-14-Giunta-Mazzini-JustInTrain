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


    @Override
    public final void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    @Override
    public final void onStop() {
        spiceManager.dontNotifyAnyRequestListeners();
        spiceManager.shouldStop();
        super.onStop();
    }


    @Override
    public final void resetRequests() {
        if (spiceManager.isStarted()) {
            spiceManager.dontNotifyAnyRequestListeners();
            spiceManager.shouldStop();
            spiceManager.start(getActivity());
        }
    }


    @Override
    public final void setSpiceManager(final SpiceManager pSpiceManager) {
        this.spiceManager = pSpiceManager;
    }


    @Override
    public final SpiceManager getSpiceManager() {
        return spiceManager;
    }
}