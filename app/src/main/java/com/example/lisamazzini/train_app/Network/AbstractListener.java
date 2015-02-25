package com.example.lisamazzini.train_app.network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.lisamazzini.train_app.exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.exceptions.NoSolutionsAvailableException;
import com.example.lisamazzini.train_app.gui.activity.MainActivity;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.TextConstants;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import retrofit.RetrofitError;

/**
 * Classe astratta per i RequestListener necessari per il funzionamento di Robospice.
 * La implementeranno i lister all'interno delle acitivity che fanno uso di robospice.
 *
 * @param <X>
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public abstract class AbstractListener<X> implements RequestListener<X> {

    private final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getDialogContext());

    /**
     * Getter per il dialogBuilder.
     * @return Ritorna il dialogBuilder
     */
    public final AlertDialog.Builder getDialogBuilder() {
        return dialogBuilder;
    }

    /**
     * Metodo implementato che mostra un Dialog di errore se la richiesta non è andata a buon fine, diverso
     * in base al tipo di Exception che viene lanciata.
     * @param spiceException spiceException
     */
    public final void onRequestFailure(final SpiceException spiceException) {
        if (spiceException.getCause() instanceof InvalidTrainNumberException) {
            showDialog(TextConstants.WRONG_TRAIN_TITLE, TextConstants.WRONG_TRAIN);
        } else if (spiceException.getCause() instanceof InvalidStationException) {
            showDialog(TextConstants.WRONG_STATION_TITLE, TextConstants.WRONG_STATION);
        } else if (spiceException.getCause() instanceof NoSolutionsAvailableException) {
            showDialog(TextConstants.NO_AVAILABLE_SOLUTION_TITLE, TextConstants.NO_AVAILABLE_SOLUTION);
        } else if (spiceException.getCause() instanceof RetrofitError) {
            showDialog(TextConstants.SERVICE_NOT_AVAILABLE_TITLE, TextConstants.SERVICE_NOT_AVAILABLE);
        } else {
            showDialog(TextConstants.CONNECTION_ERROR_TITLE, TextConstants.CONNECTION_ERROR);
            Toast.makeText(getDialogContext(),
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void showDialog(final String title, final String body) {
        dialogBuilder.setTitle(title)
                .setMessage(body)
                .setNeutralButton(TextConstants.OK_MSG, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        final Intent intent = new Intent(getDialogContext(), MainActivity.class);
                        getDialogContext().startActivity(intent);
                    }
                }).show();
    }

    /**
     * Metodo da implementare che restituisca il Context in cui si trova il Listener, necessario per
     * mostrare il Dialog.
     * @return context
     */
    public abstract Context getDialogContext();

    /**
     * Metodo da implementare che esegue azioni con il risultato della Request.
     * @param result il tipo di risultato della request
     */
    @Override
    public abstract void onRequestSuccess(final X result);

}

