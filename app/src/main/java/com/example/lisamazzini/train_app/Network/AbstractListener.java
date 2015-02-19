package com.example.lisamazzini.train_app.Network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Exceptions.NoSolutionsAvailableException;
import com.example.lisamazzini.train_app.GUI.Activity.MainActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.google.gson.JsonSyntaxException;
import com.octo.android.robospice.exception.NetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
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

    protected final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getDialogContext());

    /**
     * Metodo implementato che mostra un Dialog di errore se la richiesta non è andata a buon fine, diverso
     * in base al tipo di Exception che viene lanciata
     * @param spiceException
     */
    public void onRequestFailure(final SpiceException spiceException) {
        if(spiceException.getCause() instanceof InvalidTrainNumberException){
            showDialog(Constants.WRONG_TRAIN_TITLE, Constants.WRONG_TRAIN);
        } else if (spiceException.getCause() instanceof InvalidStationException) {
            showDialog(Constants.WRONG_STATION_TITLE, Constants.WRONG_STATION);
        }else if (spiceException.getCause() instanceof NoSolutionsAvailableException) {
            showDialog(Constants.NO_AVAILABLE_SOLUTION_TITLE, Constants.NO_AVAILABLE_SOLUTION);
        } else if (spiceException.getCause() instanceof RetrofitError) {
            showDialog(Constants.SERVICE_NOT_AVAILABLE_TITLE, Constants.SERVICE_NOT_AVAILABLE);
        } else {
            showDialog(Constants.CONNECTION_ERROR_TITLE, Constants.CONNECTION_ERROR);
            Toast.makeText(getDialogContext(),
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void showDialog(final String title, final String body) {
        dialogBuilder.setTitle(title)
                .setMessage(body)
                .setNeutralButton(Constants.OK_MSG, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getDialogContext(), MainActivity.class);
                        getDialogContext().startActivity(intent);
                    }
                }).show();
    }

    /**
     * Metodo da implementare che restituisca il Context in cui si trova il Listener, necessario per
     * mostrare il Dialog
     * @return context
     */
    public abstract Context getDialogContext();

    /**
     * Metodo da implementare che esegue azioni con il risultato della Request
     * @param result
     */
    @Override
    public abstract void onRequestSuccess(final X result);

}
