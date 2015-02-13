package com.example.lisamazzini.train_app.GUI.Fragment;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;

public abstract class AbstractRobospiceFragment extends Fragment {

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
}