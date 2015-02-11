package com.example.lisamazzini.train_app.GUI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.GUI.Fragment.NavigationDrawerFragment;

public interface INavgationDrawerUtils extends NavigationDrawerFragment.NavigationDrawerCallbacks, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    NavigationDrawerFragment getNavigationDrawerFragment();

    void showTimePickerDialog(View v);

    void showDatePickerDialog(View v);

    void onTimeSet(TimePicker view, int hourOfDay, int minute);

    void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);

    void onNavigationDrawerItemSelected(int position);



}
