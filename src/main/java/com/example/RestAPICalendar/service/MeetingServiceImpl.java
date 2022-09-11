package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.dao.MeetingDAO;
import com.example.RestAPICalendar.entity.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{
    private final MeetingDAO meetingDAO;

    @Override
    public List<Meeting> findAll() {
        return null;
    }

    @Override
    public Meeting findById(int theId) {
        return null;
    }

    @Override
    public Meeting save(Meeting theMeeting) {
        return meetingDAO.save(theMeeting);
    }

    @Override
    public void deleteById(int theId) {

    }
}
