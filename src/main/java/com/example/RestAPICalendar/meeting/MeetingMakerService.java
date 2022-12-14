package com.example.RestAPICalendar.meeting;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.entity.Meeting;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.RestAPICalendar.meeting.AppUtils.getMinutesFromTime;
import static com.example.RestAPICalendar.meeting.AppUtils.getTimeFromMinutes;

@Service
public class MeetingMakerService {
    public List<MeetTime> calculateMeetings(Calendar calendarOne, Calendar calendarTwo, int duration){
        List<Meeting> actualMeetings = collectAllActualMeetings(calendarOne, calendarTwo);
        List<MeetTime> possibilityMeetings = listingAllPossibilityMeetings(calendarOne, calendarTwo, duration);
        List<MeetTime> finalMeetings = deleteWrongMeetings(actualMeetings, possibilityMeetings);
        return finalMeetings;
    }

    public List<MeetTime> deleteWrongMeetings(List<Meeting> actualMeetings, List<MeetTime> meetTimes){
        List<MeetTime> finalMeetings = new ArrayList<>();
        for(MeetTime meetTime : meetTimes){
            boolean isOk = true;
            for(Meeting meeting : actualMeetings){
                if(getMinutesFromTime(meetTime.getStart()) >= getMinutesFromTime(meeting.getStart()) && getMinutesFromTime(meetTime.getEnd()) <= getMinutesFromTime(meeting.getEnd())) {
                    isOk = false;
                }
            }
            if(isOk)finalMeetings.add(meetTime);
        }
        return finalMeetings;
    }

    public List<MeetTime> listingAllPossibilityMeetings(Calendar calendarOne, Calendar calendarTwo, int duration){
        int calendarOneStartTime = getMinutesFromTime(calendarOne.getWorkingTime().getStart());
        int calendarOneEndTime = getMinutesFromTime(calendarOne.getWorkingTime().getEnd());
        int calendarTwoStartTime = getMinutesFromTime(calendarTwo.getWorkingTime().getStart());
        int calendarTwoEndTime = getMinutesFromTime(calendarTwo.getWorkingTime().getEnd());
        int endTimeMeet, startTimeMeet;

        List<Meeting> actualMeetings = collectAllActualMeetings(calendarOne, calendarTwo);
        List<MeetTime> newMeetings = new ArrayList<>();

        startTimeMeet = Math.max(calendarOneStartTime,calendarTwoStartTime);
        for(Meeting meeting : actualMeetings){
            endTimeMeet = getMinutesFromTime(meeting.getStart());
            addingNewTimeMeeting(newMeetings,startTimeMeet,endTimeMeet, duration);
            startTimeMeet = getMinutesFromTime(meeting.getEnd());
        }
        endTimeMeet = Math.min(calendarOneEndTime, calendarTwoEndTime);

        addingNewTimeMeeting(newMeetings,startTimeMeet,endTimeMeet, duration);

        return newMeetings;
    }

    public List<Meeting> collectAllActualMeetings(Calendar calendarOne, Calendar calendarTwo){
        List<Meeting> actualMeetings = new ArrayList<>();
        actualMeetings.addAll(calendarOne.getMeetings());
        actualMeetings.addAll(calendarTwo.getMeetings());
        actualMeetings.sort(new MeetingComparator());
        return actualMeetings;
    }

    public void addingNewTimeMeeting(List<MeetTime> meetings, int startTimeMeet, int endTimeMeet, int duration){
        if(endTimeMeet - startTimeMeet >= duration){
            MeetTime meetTime = new MeetTime(getTimeFromMinutes(startTimeMeet),getTimeFromMinutes(endTimeMeet));
            meetings.add(meetTime);
        }
    }
}
