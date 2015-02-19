package com.example.lisamazzini.train_app.GUI.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.lisamazzini.train_app.R;

/**
 * Classe astratta che devono estendere tutte le activity, per poter disporre di una toolbar uniforme in tutte le schermate
 * (con titolo, pulsante per preferire, e pulsante indietro).
 *
 * @author albertogiunta
 */
public abstract class AbstractBaseActivity extends ActionBarActivity {

    /**
     * Metodo che va chiamato nel onCreate() dell'activity, se si vuole che questa disponga della suddetta toolbar.
     */
    protected void getToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(setToolbarTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Metodo astratto da implementare per settare un titolo specifico per l'activity (è il titolo prima che il fragment esegua una qualsiavoglia richiesta)
     * @return String: il titolo dell'activity.
     */
    protected abstract String setToolbarTitle();

    /**
     * Metodo astratto da implementare per ricevere gli specifici intent dell'activity.
     */
    protected abstract void getIntents();
}
