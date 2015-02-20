package com.example.lisamazzini.train_app.gui.Fragment.DateTimePickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements IPicker {

    private DatePickerDialog.OnDateSetListener dateListener;
    private int year, month, day;

    public DatePickerFragment() {
    }

    public final void setCallback(final DatePickerDialog.OnDateSetListener dateListener) {
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
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), dateListener, year, month, day);
    }
}