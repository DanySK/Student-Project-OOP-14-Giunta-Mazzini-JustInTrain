package com.example.lisamazzini.train_app.GUI;


import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers.DatePickerFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.NavigationDrawerFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.DateTimePickers.TimePickerFragment;
import com.example.lisamazzini.train_app.R;

public class NavigationDrawerUtils implements INavgationDrawerUtils {

    private FragmentActivity activity;
    private NavigationDrawerFragment fragment;

    public NavigationDrawerUtils(FragmentActivity activity) {
        this.activity = activity;
        this.fragment = (NavigationDrawerFragment)activity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    public NavigationDrawerFragment getNavigationDrawerFragment() {
        return this.fragment;
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
           fragment.setTime(hourOfDay, minute, true);
        }
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.isShown()) {
            fragment.setDate(year, monthOfYear, dayOfMonth, true);
        }
    }

    public void onNavigationDrawerItemSelected(int position) {

    }
}
