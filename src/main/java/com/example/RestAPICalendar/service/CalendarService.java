package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.entity.Calendar;

import java.util.List;

public interface CalendarService {
    List<Calendar> findAll();
    Calendar findById(int theId);
    Calendar save(Calendar theCalendar);
    void deleteById(int theId);
}
