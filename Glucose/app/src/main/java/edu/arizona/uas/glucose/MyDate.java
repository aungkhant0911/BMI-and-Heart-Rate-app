package edu.arizona.uas.glucose;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDate {
     public final  int day, month, year;

    public MyDate(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        year = c.get(Calendar.YEAR); // current year
        month = c.get(Calendar.MONTH); // current month
        day = c.get(Calendar.DAY_OF_MONTH); // current day
    }

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month ;
        this.year = year;
    }

    public MyDate(String dateStr) {
        String[] date = dateStr.split("/");
        day = Integer.valueOf(date[0]);
        month = Integer.valueOf(date[1]); // due to java starting month at 0
        year = Integer.valueOf(date[2]);
    }

    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
