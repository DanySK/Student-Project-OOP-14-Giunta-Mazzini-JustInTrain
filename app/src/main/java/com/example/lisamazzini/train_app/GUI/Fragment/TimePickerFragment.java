package com.example.lisamazzini.train_app.GUI.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));


//        final Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(),
//                hour,
//                minute,
//                DateFormat.is24HourFormat(getActivity()));
    }
}
