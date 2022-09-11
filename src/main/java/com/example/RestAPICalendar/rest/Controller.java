package com.example.RestAPICalendar.rest;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.entity.Meeting;
import com.example.RestAPICalendar.model.MeetTime;
import com.example.RestAPICalendar.service.CalendarService;
import com.example.RestAPICalendar.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
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

    @GetMapping("/meetings/{calendarOneId}/{calendarTwoId}/{meetingTime}")
    public CollectionModel<EntityModel<MeetTime>> getMeetings(@PathVariable int calendarOneId, @PathVariable int calendarTwoId, @PathVariable int meetingTime){
        List<EntityModel<MeetTime>> meetTimes = calendarService.meetingCalculator(calendarOneId, calendarTwoId, meetingTime).stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());
        return CollectionModel.of(meetTimes);
    }
}
