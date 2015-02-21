package com.example.lisamazzini.train_app.gui.fragment.pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

/**
 * Fragment che permette di visualizzare un time picker.
 *
 * @author albertogiunta
 */
public class TimePickerFragment extends DialogFragment implements IPicker<TimePickerDialog.OnTimeSetListener> {

    private TimePickerDialog.OnTimeSetListener timeListener;
    private int hour, minute;

    @Override
    public final void setCallback(final TimePickerDialog.OnTimeSetListener pListener) {
        this.timeListener = pListener;
    }

    @Override
    public final void setArguments(final Bundle args) {
        super.setArguments(args);
        this.hour = args.getInt("hour");
        this.minute = args.getInt("minute");

    }

    @Override
    public final Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), timeListener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}