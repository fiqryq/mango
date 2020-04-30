package com.mango.autumnleaves.util;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FunctionHelper {

    public static class Func {

        public static String getNameDay() {
            Locale id = new Locale("in", "ID");
            String pattern = "EEEE";
            Date today = new Date();

            // Gets formatted date specify by the given pattern for
            // Indonesian Locale no changes for default date format
            // is applied here.
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, id);
            return sdf.format(today);
        }

        public static String getTimeNow() {
            Locale id = new Locale("in", "ID");
            String pattern = "EEEE, dd MMMM yyyy";
            Date today = new Date();

            // Gets formatted date specify by the given pattern for
            // Indonesian Locale no changes for default date format
            // is applied here.
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, id);
            return sdf.format(today);
        }

        public static String getHour(){
            Date dateNow = Calendar.getInstance().getTime();
            String hour = (String) android.text.format.DateFormat.format("HH:mm", dateNow);
            return hour;
        }
    }
}
