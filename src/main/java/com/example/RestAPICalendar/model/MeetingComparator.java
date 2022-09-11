package com.example.RestAPICalendar.model;

import com.example.RestAPICalendar.entity.Meeting;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class MeetingComparator implements Comparator<Meeting> {
    @Override
    public int compare(Meeting o1, Meeting o2) {
        if(getMinutesFromTime(o1.getStart()) != getMinutesFromTime(o2.getStart())){
            return Integer.compare(getMinutesFromTime(o1.getStart()),getMinutesFromTime(o2.getStart()));
        } else {
            return Integer.compare(getMinutesFromTime(o1.getEnd()),getMinutesFromTime(o2.getEnd()));
        }
    }

    public int getMinutesFromTime(String time){
        return Integer.parseInt(time.substring(3,5)) + Integer.parseInt(time.substring(0,2))*60;
    }
}
