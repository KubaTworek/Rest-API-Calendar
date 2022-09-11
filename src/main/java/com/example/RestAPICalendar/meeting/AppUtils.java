package com.example.RestAPICalendar.meeting;

public class AppUtils {
    public static int getMinutesFromTime(String time){
        return Integer.parseInt(time.substring(3,5)) + Integer.parseInt(time.substring(0,2))*60;
    }

    public static String getTimeFromMinutes(int mins){
        int hours = mins / 60;
        int minutes = mins % 60;
        String hoursStr = (hours > 9) ? String.valueOf(hours) : "0"+hours;
        String minutesStr = (minutes > 9) ? String.valueOf(minutes) : "0"+minutes;
        return hoursStr + ":" + minutesStr;
    }
}
