package com.example.dosificapp.dominio;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarItem {
    private Calendar calendar;
    private ArrayList<String> names;
    private int hour;

    public CalendarItem(Calendar calendar, int hour) {
        names = new ArrayList<String>();
        this.hour = hour;
        this.calendar = calendar;
    }

    public String getName(int i){
        return names.get(i);
    }

    public void addMed(String name){
        names.add(name);
    }

    public String getHsString(){
        return String.valueOf(this.hour);
    }

    public String getAmPmString(){
        return String.valueOf(this.calendar.get(Calendar.AM_PM));
    }
}
