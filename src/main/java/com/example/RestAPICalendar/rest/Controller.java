package com.example.RestAPICalendar.rest;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.service.CalendarService;
import com.example.RestAPICalendar.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class Controller {
    private final CalendarService calendarService;
    private final MeetingService meetingService;

    @PostMapping( "/calendars")
    public ResponseEntity<EntityModel<Calendar>> saveCalendar(@RequestBody Calendar theCalendar){

    }

    @GetMapping("/meetings/{calendarOneId}/{calendarTwoId}/{meetingTime}")
    public CollectionModel<List<Object>> getMeetings(@PathVariable int calendarOneId, @PathVariable int calendarTwoId, @PathVariable int meetingTime){

    }
}
