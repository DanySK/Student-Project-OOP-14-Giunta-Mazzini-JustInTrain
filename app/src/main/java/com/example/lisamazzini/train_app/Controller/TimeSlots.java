package com.example.lisamazzini.train_app.Controller;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public enum TimeSlots {

    EARLY_MORNING(1, "00:00", new DateTime()),
    MORNING(2, "06:00", new DateTime()),
    AFTERNOON(3, "13:00", new DateTime()),
    EVENING(4, "18:00", new DateTime()),
    NIGHT(5, "22:00", new DateTime()),
    LATE_NIGHT(6, "23:59", new DateTime()),
    NOW(0, new SimpleDateFormat().format(Calendar.getInstance().getTime()), new DateTime());


    private int index;
    private String dateTimeString;
    private DateTime dateTime;

    private TimeSlots(int index, String dateTimeString, DateTime dateTime) {
        try {
            this.index = index;
            this.dateTimeString = dateTimeString;
            dateTime = new DateTime(new SimpleDateFormat("HH:mm").parse(this.dateTimeString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getIndex() {
        return index;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public boolean isMinorThan (TimeSlots other) {
        return Minutes.minutesBetween(this.getDateTime(), TimeSlots.NOW.getDateTime()).getMinutes() < 0;
    }
}
