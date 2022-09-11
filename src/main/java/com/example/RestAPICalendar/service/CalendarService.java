package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.meeting.MeetTime;

import java.util.List;

public interface CalendarService {
    List<Calendar> findAll();
    Calendar findById(int theId);
    Calendar save(Calendar theCalendar);
    List<MeetTime> meetingCalculator(int calendarOneId, int calendarTwoId, int duration);
}
