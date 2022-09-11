package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.dao.CalendarDAO;
import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.entity.Meeting;
import com.example.RestAPICalendar.model.MeetTime;
import com.example.RestAPICalendar.model.MeetingComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
    private final CalendarDAO calendarDAO;
    private final MeetingComparator meetingComparator;

    @Override
    public List<Calendar> findAll() {
        return calendarDAO.findAll();
    }

    @Override
    public Calendar findById(int theId) {
        return calendarDAO.findById(theId).orElse(null);
    }

    @Override
    public Calendar save(Calendar theCalendar) {
        return calendarDAO.save(theCalendar);
    }

    @Override
    public void deleteById(int theId) {

    }

    public List<MeetTime> meetingCalculator(int calendarOneId, int calendarTwoId, int duration){
        Calendar calendarOne = findById(calendarOneId);
        Calendar calendarTwo = findById(calendarTwoId);

        int calendarOneStartTime = getMinutesFromTime(calendarOne.getWorkingTime().getStart());
        int calendarOneEndTime = getMinutesFromTime(calendarOne.getWorkingTime().getEnd());
        int calendarTwoStartTime = getMinutesFromTime(calendarTwo.getWorkingTime().getStart());
        int calendarTwoEndTime = getMinutesFromTime(calendarTwo.getWorkingTime().getEnd());

        List<MeetTime> newMeetings = new ArrayList<>();
        List<Meeting> actualMeetings = new ArrayList<>();
        List<MeetTime> finalMeetings = new ArrayList<>();
        int endTimeMeet, startTimeMeet;

        actualMeetings.addAll(calendarOne.getMeetings());
        actualMeetings.addAll(calendarTwo.getMeetings());
        actualMeetings.sort(meetingComparator);

        startTimeMeet = Math.max(calendarOneStartTime,calendarTwoStartTime);
        for(Meeting meeting : actualMeetings){
            endTimeMeet = getMinutesFromTime(meeting.getStart());
            addingNewTimeMeeting(newMeetings,startTimeMeet,endTimeMeet, duration);
            startTimeMeet = getMinutesFromTime(meeting.getEnd());
        }
        endTimeMeet = Math.min(calendarOneEndTime, calendarTwoEndTime);

        addingNewTimeMeeting(newMeetings,startTimeMeet,endTimeMeet, duration);

        for(MeetTime meetTime : newMeetings){
            boolean isOk = true;
            for(Meeting meeting : actualMeetings){
                if(getMinutesFromTime(meetTime.getStart()) > getMinutesFromTime(meeting.getStart()) && getMinutesFromTime(meetTime.getEnd()) < getMinutesFromTime(meeting.getEnd())) {
                    isOk = false;
                }
            }
            if(isOk)finalMeetings.add(meetTime);
        }

        return finalMeetings;
    }
    public int getMinutesFromTime(String time){
        return Integer.parseInt(time.substring(3,5)) + Integer.parseInt(time.substring(0,2))*60;
    }

    public String getTimeFromMinutes(int mins){
        int hours = mins / 60;
        int minutes = mins % 60;
        String hoursStr = (hours > 9) ? String.valueOf(hours) : "0"+hours;
        String minutesStr = (minutes > 9) ? String.valueOf(minutes) : "0"+minutes;
        return hoursStr + ":" + minutesStr;
    }

    public void addingNewTimeMeeting(List<MeetTime> meetings, int startTimeMeet, int endTimeMeet, int duration){
        if(endTimeMeet - startTimeMeet >= duration){
            MeetTime meetTime = new MeetTime(getTimeFromMinutes(startTimeMeet),getTimeFromMinutes(endTimeMeet));
            meetings.add(meetTime);
        }
    }
}
