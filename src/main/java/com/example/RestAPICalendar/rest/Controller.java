package com.example.RestAPICalendar.rest;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.entity.Meeting;
import com.example.RestAPICalendar.errors.TimeIsNullException;
import com.example.RestAPICalendar.errors.WorkingTimeIsNullException;
import com.example.RestAPICalendar.errors.WrongTimeException;
import com.example.RestAPICalendar.meeting.MeetTime;
import com.example.RestAPICalendar.service.CalendarService;
import com.example.RestAPICalendar.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class Controller {
    public static final int EMPTY_ID = 0;
    private final CalendarService calendarService;
    private final MeetingService meetingService;

    @GetMapping("/calendars")
    public CollectionModel<EntityModel<Calendar>> getCalendars(){
        List<EntityModel<Calendar>> calendars = calendarService.findAll().stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());
        return CollectionModel.of(calendars);
    }

    @PostMapping( "/calendars")
    public ResponseEntity<EntityModel<Calendar>> saveCalendar(@RequestBody Calendar theCalendar){
        if(isWorkingTimeExist(theCalendar) && isTimeRight(theCalendar)){
            for(Meeting meeting : theCalendar.getMeetings()){
                meetingService.save(meeting);
            }
            Calendar calendar = calendarService.save(new Calendar(
                    EMPTY_ID,
                    theCalendar.getWorkingTime(),
                    theCalendar.getMeetings()
            ));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(EntityModel.of(calendar));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meetings/{calendarOneId}/{calendarTwoId}/{meetingTime}")
    public CollectionModel<EntityModel<MeetTime>> getMeetings(@PathVariable int calendarOneId, @PathVariable int calendarTwoId, @PathVariable int meetingTime){
        List<EntityModel<MeetTime>> meetTimes = calendarService.meetingCalculator(calendarOneId, calendarTwoId, meetingTime).stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());
        return CollectionModel.of(meetTimes);
    }

    private boolean isWorkingTimeExist(Calendar calendar){
        if(calendar.getWorkingTime() == null) throw new WorkingTimeIsNullException("Working time is null");
        return true;
    }

    private boolean isTimeRight(Calendar calendar){
        Pattern pattern = Pattern.compile("[0-2][0-9]:[0-5][0-9]");
        if(calendar.getWorkingTime().getStart() == null || calendar.getWorkingTime().getEnd() == null) throw new TimeIsNullException("Time is wrong");
        if(!(pattern.matcher(calendar.getWorkingTime().getStart()).matches() && pattern.matcher(calendar.getWorkingTime().getEnd()).matches())) throw new WrongTimeException("Time is wrong");
        for(Meeting tempMeeting : calendar.getMeetings()){
            if(tempMeeting.getStart() == null || tempMeeting.getEnd() == null) throw new TimeIsNullException("Time is wrong");
            if(!(pattern.matcher(tempMeeting.getStart()).matches() && pattern.matcher(tempMeeting.getEnd()).matches())) throw new WrongTimeException("Time is wrong");
        }
        return true;
    }
}
