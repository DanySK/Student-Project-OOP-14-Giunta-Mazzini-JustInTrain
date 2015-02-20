package com.example.lisamazzini.train_app.gui.fragment;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;

public abstract class AbstractRobospiceFragment extends Fragment implements IBaseFragment {

    protected SpiceManager spiceManager;

    @Override
    public void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    @Override
    public void onStop() {
        spiceManager.dontNotifyAnyRequestListeners();
        spiceManager.shouldStop();
        super.onStop();
    }

    public void resetRequests() {
        if (spiceManager.isStarted()) {
            spiceManager.dontNotifyAnyRequestListeners();
            spiceManager.shouldStop();
            spiceManager.start(getActivity());
        }
    }
}