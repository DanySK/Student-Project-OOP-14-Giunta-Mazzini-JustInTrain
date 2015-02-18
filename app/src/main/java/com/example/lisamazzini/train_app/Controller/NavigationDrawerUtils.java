package com.example.lisamazzini.train_app.Controller;


import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers.DatePickerFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.NavigationDrawerFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers.TimePickerFragment;
import com.example.lisamazzini.train_app.R;

public class NavigationDrawerUtils implements INavigationDrawerUtils {

    private FragmentActivity activity;
    private NavigationDrawerFragment fragment;

    public NavigationDrawerUtils(final FragmentActivity activity) {
        this.activity = activity;
        this.fragment = (NavigationDrawerFragment)activity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    public NavigationDrawerFragment getNavigationDrawerFragment() {
        return this.fragment;
    }

    public void showTimePickerDialog(final View v) {
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(final View v) {
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }

    public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
        if (view.isShown()) {
           fragment.setTime(hourOfDay, minute, true);
        }
    }

    public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
//        if (view.isShown()) {
//            fragment.setDate(year, monthOfYear, dayOfMonth, true);
//        }
    }

    public void onNavigationDrawerItemSelected(final int position) {

    }
}