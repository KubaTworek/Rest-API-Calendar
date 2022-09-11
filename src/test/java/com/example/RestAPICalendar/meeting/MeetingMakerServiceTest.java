package com.example.RestAPICalendar.meeting;

import com.example.RestAPICalendar.entity.Meeting;
import com.example.RestAPICalendar.service.CalendarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MeetingMakerServiceTest {
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MeetingMakerService meetingMakerService;
    @Autowired
    private CalendarService calendarService;

    @BeforeEach
    public void setup() {
        jdbc.execute("DELETE FROM Meeting");
        jdbc.execute("DELETE FROM Calendar");
        jdbc.execute("DELETE FROM Working");
        jdbc.execute("INSERT INTO Working(id, start, end)" + "VALUES (1,'09:00','19:55')");
        jdbc.execute("INSERT INTO Calendar(id, working_id)" + "VALUES (1,1)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (1,'09:00','10:30',1)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (2,'12:00','13:00',1)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (3,'16:00','18:00',1)");
        jdbc.execute("INSERT INTO Working(id, start, end)" + "VALUES (2,'10:00','18:30')");
        jdbc.execute("INSERT INTO Calendar(id, working_id)" + "VALUES (2,2)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (4,'10:00','11:30',2)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (5,'12:30','14:30',2)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (6,'14:30','15:00',2)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (7,'16:00','17:00',2)");
        jdbc.execute("INSERT INTO Working(id, start, end)" + "VALUES (3,'10:00','18:00')");
        jdbc.execute("INSERT INTO Calendar(id, working_id)" + "VALUES (3,3)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (8,'10:00','16:00',3)");

    }

    @Test
    public void calculateMeetingsTest(){
        List<MeetTime> meetingsOne = meetingMakerService.calculateMeetings(calendarService.findById(1),calendarService.findById(2),30);
        List<MeetTime> meetingsTwo = meetingMakerService.calculateMeetings(calendarService.findById(2),calendarService.findById(1),30);
        List<MeetTime> meetingsThree = meetingMakerService.calculateMeetings(calendarService.findById(2),calendarService.findById(3),30);

        assertEquals(3,meetingsOne.size());
        assertEquals(3,meetingsTwo.size());
        assertEquals(1,meetingsThree.size());
        assertEquals("11:30",meetingsOne.get(0).getStart());
        assertEquals("12:00",meetingsOne.get(0).getEnd());
        assertEquals("15:00",meetingsOne.get(1).getStart());
        assertEquals("16:00",meetingsOne.get(1).getEnd());
        assertEquals("18:00",meetingsOne.get(2).getStart());
        assertEquals("18:30",meetingsOne.get(2).getEnd());
        assertEquals("11:30",meetingsTwo.get(0).getStart());
        assertEquals("12:00",meetingsTwo.get(0).getEnd());
        assertEquals("15:00",meetingsTwo.get(1).getStart());
        assertEquals("16:00",meetingsTwo.get(1).getEnd());
        assertEquals("18:00",meetingsTwo.get(2).getStart());
        assertEquals("18:30",meetingsTwo.get(2).getEnd());
        assertEquals("17:00",meetingsThree.get(0).getStart());
        assertEquals("18:00",meetingsThree.get(0).getEnd());
    }

    @Test
    public void deleteWrongMeetingsTest(){
        List<MeetTime> meetings = meetingMakerService.listingAllPossibilityMeetings(calendarService.findById(2),calendarService.findById(3),30);
        meetings = meetingMakerService.deleteWrongMeetings(meetingMakerService.collectAllActualMeetings(calendarService.findById(2),calendarService.findById(3)),meetings);

        assertEquals(1,meetings.size());
        assertEquals("17:00",meetings.get(0).getStart());
        assertEquals("18:00",meetings.get(0).getEnd());
    }

    @Test
    public void listingAllPossibilityMeetingsTest(){
        List<MeetTime> meetings = meetingMakerService.listingAllPossibilityMeetings(calendarService.findById(2),calendarService.findById(3),30);

        assertEquals(2,meetings.size());
        assertEquals("15:00",meetings.get(0).getStart());
        assertEquals("16:00",meetings.get(0).getEnd());
        assertEquals("17:00",meetings.get(1).getStart());
        assertEquals("18:00",meetings.get(1).getEnd());
    }

    @Test
    public void collectAllActualMeetingsTest(){
        List<Meeting> meetingsOne = meetingMakerService.collectAllActualMeetings(calendarService.findById(1),calendarService.findById(2));
        List<Meeting> meetingsTwo = meetingMakerService.collectAllActualMeetings(calendarService.findById(2),calendarService.findById(1));
        List<Meeting> meetingsThree = meetingMakerService.collectAllActualMeetings(calendarService.findById(2),calendarService.findById(3));

        assertEquals(7,meetingsOne.size());
        assertEquals(7,meetingsTwo.size());
        assertEquals(5,meetingsThree.size());

        assertEquals("09:00",meetingsOne.get(0).getStart());
        assertEquals("10:30",meetingsOne.get(0).getEnd());
        assertEquals("10:00",meetingsOne.get(1).getStart());
        assertEquals("11:30",meetingsOne.get(1).getEnd());
        assertEquals("12:00",meetingsOne.get(2).getStart());
        assertEquals("13:00",meetingsOne.get(2).getEnd());
        assertEquals("12:30",meetingsOne.get(3).getStart());
        assertEquals("14:30",meetingsOne.get(3).getEnd());
        assertEquals("14:30",meetingsOne.get(4).getStart());
        assertEquals("15:00",meetingsOne.get(4).getEnd());
        assertEquals("16:00",meetingsOne.get(5).getStart());
        assertEquals("17:00",meetingsOne.get(5).getEnd());
        assertEquals("16:00",meetingsOne.get(6).getStart());
        assertEquals("18:00",meetingsOne.get(6).getEnd());

        assertEquals("09:00",meetingsTwo.get(0).getStart());
        assertEquals("10:30",meetingsTwo.get(0).getEnd());
        assertEquals("10:00",meetingsTwo.get(1).getStart());
        assertEquals("11:30",meetingsTwo.get(1).getEnd());
        assertEquals("12:00",meetingsTwo.get(2).getStart());
        assertEquals("13:00",meetingsTwo.get(2).getEnd());
        assertEquals("12:30",meetingsTwo.get(3).getStart());
        assertEquals("14:30",meetingsTwo.get(3).getEnd());
        assertEquals("14:30",meetingsTwo.get(4).getStart());
        assertEquals("15:00",meetingsTwo.get(4).getEnd());
        assertEquals("16:00",meetingsTwo.get(5).getStart());
        assertEquals("17:00",meetingsTwo.get(5).getEnd());
        assertEquals("16:00",meetingsTwo.get(6).getStart());
        assertEquals("18:00",meetingsTwo.get(6).getEnd());

        assertEquals("10:00",meetingsThree.get(0).getStart());
        assertEquals("11:30",meetingsThree.get(0).getEnd());
        assertEquals("10:00",meetingsThree.get(1).getStart());
        assertEquals("16:00",meetingsThree.get(1).getEnd());
        assertEquals("12:30",meetingsThree.get(2).getStart());
        assertEquals("14:30",meetingsThree.get(2).getEnd());
        assertEquals("14:30",meetingsThree.get(3).getStart());
        assertEquals("15:00",meetingsThree.get(3).getEnd());
        assertEquals("16:00",meetingsThree.get(4).getStart());
        assertEquals("17:00",meetingsThree.get(4).getEnd());
    }

    @Test
    public void addingNewTimeMeetingTest(){
        List<MeetTime> meetings = new ArrayList<>();
        meetingMakerService.addingNewTimeMeeting(meetings,10,20,15);
        assertEquals(0,meetings.size());
        meetingMakerService.addingNewTimeMeeting(meetings,10,20,10);
        assertEquals(1,meetings.size());
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("DELETE FROM Meeting");
        jdbc.execute("DELETE FROM Calendar");
        jdbc.execute("DELETE FROM Working");
    }
}
