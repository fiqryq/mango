package com.mango.autumnleaves.util;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Waktu {

    public static void getwaktu(){
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) android.text.format.DateFormat.format("d",   date);
        String monthNumber  = (String) android.text.format.DateFormat.format("M",   date);
        String year         = (String) DateFormat.format("yyyy", date);

        int month = Integer.parseInt(monthNumber);
        String bulan = null;

        if (month == 1){
            bulan = "Januari";
        }else if (month == 2){
            bulan = "Februari";
        }else if (month == 3){
            bulan = "Maret";
        }else if (month == 4){
            bulan = "April";
        }else if (month == 5){
            bulan = "Mei";
        }else if (month == 6){
            bulan = "Juni";
        }else if (month == 7){
            bulan = "Juli";
        }else if (month == 8){
            bulan = "Agustus";
        }else if (month == 9){
            bulan = "September";
        }else if (month == 10){
            bulan = "Oktober";
        }else if (month == 11){
            bulan = "November";
        }else if (month == 12){
            bulan = "Desember";
        }
    }

}
