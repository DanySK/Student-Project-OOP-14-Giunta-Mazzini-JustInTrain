package com.example.lisamazzini.train_app.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.lisamazzini.train_app.GUI.MainActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by lisamazzini on 05/02/15.
 */

public abstract class AbstractListener<X> implements RequestListener<X> {

    protected final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

    public void onRequestFailure(SpiceException spiceException) {
        dialogBuilder.setTitle("Problemi di connessione")
                .setMessage("Controllare la propria connessione internet, patacca")
                .setNeutralButton("Ok" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        getContext().startActivity(intent);
                    }
                }).show();

        Toast.makeText(getContext(),
                "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                .show();

    }

    public abstract Context getContext();

    @Override
    public abstract void onRequestSuccess(X result);

}

