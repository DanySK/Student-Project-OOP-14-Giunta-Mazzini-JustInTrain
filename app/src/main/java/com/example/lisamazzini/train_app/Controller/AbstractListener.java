package com.example.lisamazzini.train_app.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.GUI.Activity.MainActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.octo.android.robospice.exception.NetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public abstract class AbstractListener<X> implements RequestListener<X> {

    protected final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getDialogContext());

    public void onRequestFailure(SpiceException spiceException) {
        if(spiceException.getCause() instanceof InvalidTrainNumberException){
            dialogBuilder.setTitle(Constants.WRONG_TRAIN_TITLE)
                    .setMessage(Constants.WRONG_TRAIN)
                    .setNeutralButton(Constants.OK_MSG, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getDialogContext(), MainActivity.class);
                            getDialogContext().startActivity(intent);
                        }
                    })
                    .show();
        } else if (spiceException.getCause() instanceof InvalidStationException) {
            dialogBuilder.setTitle(Constants.WRONG_STATION_TITLE)
                    .setMessage(Constants.WRONG_STATION)
                    .setNeutralButton(Constants.OK_MSG, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getDialogContext(), MainActivity.class);
                            getDialogContext().startActivity(i);
                        }
                    }).show();
        } else {
            dialogBuilder.setTitle(Constants.CONNECTION_ERROR_TITLE)
                    .setMessage(Constants.CONNECTION_ERROR)
                    .setNeutralButton(Constants.OK_MSG, new DialogInterface.OnClickListener() {
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

