package com.iceblizzard.advancecombat.utils;

public class TimeUnitUtil {


    public static long milliToSecond(long value) {
        return value / 1000;
    }

    public static long secondToMilli(long value) {
        return value * 1000;
    }

    public static long secondToMinute(long value) {
        return value / 60;
    }
}
