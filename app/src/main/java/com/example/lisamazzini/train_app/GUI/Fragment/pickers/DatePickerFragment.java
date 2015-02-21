package com.example.lisamazzini.train_app.gui.fragment.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Fragment che permette di visualizzare un date picker.
 *
 * @author albertogiunta
 */
public class DatePickerFragment extends DialogFragment implements IPicker<DatePickerDialog.OnDateSetListener> {

    private DatePickerDialog.OnDateSetListener dateListener;
    private int year, month, day;

    @Override
    public final void setCallback(final DatePickerDialog.OnDateSetListener pListener) {
        this.dateListener = pListener;
    }

    @Override
    public final void setArguments(final Bundle args) {
        super.setArguments(args);
        this.year = args.getInt("year");
        this.month = args.getInt("month");
        this.day = args.getInt("day");
    }

    @Override
    public final Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), dateListener, year, month, day);
    }
}