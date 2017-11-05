package com.tuhin.weathora.utils;

import android.content.SharedPreferences;

import com.tuhin.weathora.app.AppController;

import java.text.DateFormat;
import java.util.Date;

public class UnitConvertor {
    private static DateFormat timeFormat;
    static {
        timeFormat = android.text.format.DateFormat.getTimeFormat(AppController.getInstance());
    }

    //Returns kelvinTemp value as Celsius
    public static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    //Returns kelvinTemp value as Fahrenheit
    public static float kelvinToFahrenheit(float kelvinTemp) {
        return (((9 * kelvinToCelsius(kelvinTemp)) / 5) + 32);
    }

    //Returns the pressure value as in Hg
    public static float convertPressure(double pressure) {
        return (float) (pressure * 0.0295299830714);
    }

    //Returns the wind value as mph
    public static double convertWind(float wind) {
        return wind * 2.23693629205;
    }

    //Capitalize the first char of a given string and return
    public static String capitalizeString(String str) {
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    //Returns the formatted date from given long value
    public static String formatDate(long time) {
        return timeFormat.format(new Date(time * 1000));
    }
}
