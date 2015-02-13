package com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers.IPicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements IPicker {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));
    }
}