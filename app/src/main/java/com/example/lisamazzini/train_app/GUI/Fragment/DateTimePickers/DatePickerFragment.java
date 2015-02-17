package com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers.IPicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements IPicker {

    DatePickerDialog.OnDateSetListener dateListener;
    private int year, month, day;

    public DatePickerFragment() {
    }


    public void setCallback(DatePickerDialog.OnDateSetListener dateListener) {
        this.dateListener = dateListener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.year = args.getInt("year");
        this.month = args.getInt("month");
        this.day = args.getInt("day");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), dateListener, year, month, day);
    }
}