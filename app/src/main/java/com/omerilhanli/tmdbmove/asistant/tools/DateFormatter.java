package com.omerilhanli.tmdbmove.asistant.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatter {

    public static String getReleaseDate() {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        return format1.format(cal.getTime());
    }
}
