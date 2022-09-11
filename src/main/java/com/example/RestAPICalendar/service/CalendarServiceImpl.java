package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.dao.CalendarDAO;
import com.example.RestAPICalendar.entity.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
    private final CalendarDAO calendarDAO;

    @Override
    public List<Calendar> findAll() {
        return calendarDAO.findAll();
    }

    @Override
    public Calendar findById(int theId) {
        return null;
    }

    @Override
    public Calendar save(Calendar theCalendar) {
        return calendarDAO.save(theCalendar);
    }

    @Override
    public void deleteById(int theId) {

    }
}
