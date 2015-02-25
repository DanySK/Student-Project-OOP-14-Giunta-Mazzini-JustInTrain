package com.example.lisamazzini.train_app.gui.fragment;

import com.octo.android.robospice.SpiceManager;

/**
 * Created by lisamazzini on 24/02/15.
 */

public interface IRobospiceFragment extends IBaseFragment {

    void resetRequests();

    void setSpiceManager(final SpiceManager pSpiceManager);

    SpiceManager getSpiceManager();

}
