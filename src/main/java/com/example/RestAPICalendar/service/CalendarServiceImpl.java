package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.dao.CalendarDAO;
import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.errors.CalendarNotFoundException;
import com.example.RestAPICalendar.meeting.MeetTime;
import com.example.RestAPICalendar.meeting.MeetingMakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
    private final CalendarDAO calendarDAO;
    private final MeetingMakerService meetingMakerService;
    public List<Calendar> findAll() {
        return calendarDAO.findAll();
    }

    @Override
    public Calendar findById(int theId) {
        return calendarDAO.findById(theId).orElseThrow(() -> new CalendarNotFoundException("Calendar id not found - " + theId));
    }

    @Override
    public Calendar save(Calendar theCalendar) {
        return calendarDAO.save(theCalendar);
    }

    @Override
    public List<MeetTime> meetingCalculator(int calendarOneId, int calendarTwoId, int duration) {
        return meetingMakerService.calculateMeetings(findById(calendarOneId), findById(calendarTwoId), duration);
    }
}
