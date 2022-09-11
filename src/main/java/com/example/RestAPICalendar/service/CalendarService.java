package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.model.MeetTime;

import java.util.List;

public interface CalendarService {
    List<Calendar> findAll();
    Calendar findById(int theId);
    Calendar save(Calendar theCalendar);
    void deleteById(int theId);
    public List<MeetTime> meetingCalculator(int calendarOneId, int calendarTwoId, int duration);
}
