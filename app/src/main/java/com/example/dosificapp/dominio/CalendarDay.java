package com.example.dosificapp.dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarDay {

    private Calendar calendar;
    private ArrayList<CalendarItem> calendarItems;

    public CalendarDay(int index){
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, index);
        calendarItems = new ArrayList<CalendarItem>();
        for(int i = 0; i < 24; i++){
            calendarItems.add(new CalendarItem(calendar, i));
        }
    }

    public void addDosis(Dosis dosis){
        int indice = dosis.getCalendar().get(Calendar.HOUR_OF_DAY);
        calendarItems.get(indice).addMed(dosis.getName());
    }

    public void addListDosis(ArrayList<Dosis> dosis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Dosis d : dosis){
            String dosisTime = sdf.format(d.getCalendar().getTime());
            String compare = sdf.format(calendar.getTime());
            if(compare.equalsIgnoreCase(dosisTime)) addDosis(d);
        }

    }

    public Calendar getCalendar() {
        return calendar;
    }

    public ArrayList<CalendarItem> getCalendarItems() {return this.calendarItems; }
}
