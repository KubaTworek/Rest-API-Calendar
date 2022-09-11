package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.entity.Meeting;

import java.util.List;

public interface MeetingService {
    List<Meeting> findAll();
    Meeting findById(int theId);
    Meeting save(Meeting theMeeting);
    void deleteById(int theId);
}
