package com.example.lisamazzini.train_app.GUI;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.lisamazzini.train_app.GUI.Fragment.DatePickerFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.NavigationDrawerFragment;
import com.example.lisamazzini.train_app.GUI.Fragment.TimePickerFragment;
import com.example.lisamazzini.train_app.R;

public class NavigationDrawerUtils implements INavgationDrawerUtils {

    private FragmentActivity activity;
    private NavigationDrawerFragment fragment;

    private TimePickerFragment timeFragment;
    private DialogFragment dateFragment;

    public NavigationDrawerUtils(FragmentActivity activity) {
        this.activity = activity;
        this.fragment = (NavigationDrawerFragment)activity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    public NavigationDrawerFragment getNavigationDrawerFragment() {
        return this.fragment;
    }

    public void showTimePickerDialog(View v) {
        timeFragment = new TimePickerFragment();
        timeFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        dateFragment = new DatePickerFragment();
        dateFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
           fragment.setTime(hourOfDay, minute);
        }
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.isShown()) {
            fragment.setDate(year, monthOfYear, dayOfMonth);
        }
    }

    public void onNavigationDrawerItemSelected(int position) {

    }
}
