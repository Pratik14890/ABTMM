package com.terapanth.abtmm.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getFormattedDateOrTime(String strOriginalDate, String currentFormat, String requiredFormat) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat(currentFormat, Locale.US);
        Date newDate = format.parse(strOriginalDate);

        format = new SimpleDateFormat(requiredFormat);
        String date = format.format(newDate);

        Log.d("TestLog", "converted date : " + date);

        return date;
    }
}
