package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.dao.MeetingDAO;
import com.example.RestAPICalendar.entity.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{
    private final MeetingDAO meetingDAO;

    @Override
    public Meeting save(Meeting theMeeting) {
        return meetingDAO.save(theMeeting);
    }
}
