package com.example.lisamazzini.train_app.gui.Fragment.DateTimePickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment implements IPicker {

    private TimePickerDialog.OnTimeSetListener timeListener;
    private int hour, minute;

    public TimePickerFragment() {
    }

    public void setCallback(final TimePickerDialog.OnTimeSetListener timeListener) {
        this.timeListener = timeListener;
    }

    @Override
    public void setArguments(final Bundle args) {
        super.setArguments(args);
        this.hour = args.getInt("hour");
        this.minute = args.getInt("minute");

    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), timeListener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}