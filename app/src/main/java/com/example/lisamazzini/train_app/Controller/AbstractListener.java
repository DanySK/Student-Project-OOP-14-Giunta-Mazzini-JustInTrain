package com.example.lisamazzini.train_app.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.GUI.Activity.MainActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by lisamazzini on 05/02/15.
 */

public abstract class AbstractListener<X> implements RequestListener<X> {

    protected final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getDialogContext());

    public void onRequestFailure(SpiceException spiceException) {
        //numero non valido
        if(spiceException.getCause() instanceof InvalidTrainNumberException){
            dialogBuilder.setTitle("Numero treno non valido!")
                    .setMessage("Il numero inserito non corrisponde a nessun cazzo di treno")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getDialogContext(), MainActivity.class);
                            getDialogContext().startActivity(intent);
                        }
                    })
                    .show();
        }else if (spiceException.getCause() instanceof InvalidStationException) {
            dialogBuilder.setTitle("Stazione inesistente!")
                    .setMessage("Il nome inserito non corrisponde a nessun risultato")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getDialogContext(), MainActivity.class);
                            getDialogContext().startActivity(i);
                        }
                    }).show();
        }else {

            dialogBuilder.setTitle("Problemi di connessione")
                    .setMessage("Controllare la propria connessione internet, patacca")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getDialogContext(), MainActivity.class);
                            getDialogContext().startActivity(intent);
                        }
                    }).show();

            Toast.makeText(getDialogContext(),
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public abstract Context getDialogContext();

    @Override
    public abstract void onRequestSuccess(X result);

}

