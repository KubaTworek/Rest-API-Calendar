package com.example.RestAPICalendar.meeting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.RestAPICalendar.meeting.AppUtils.getMinutesFromTime;
import static com.example.RestAPICalendar.meeting.AppUtils.getTimeFromMinutes;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppUtilsTest {

    @Test
    public void getMinutesFromTimeTest(){
        assertEquals(50,getMinutesFromTime("00:50"));
        assertEquals(130,getMinutesFromTime("02:10"));
        assertEquals(730,getMinutesFromTime("12:10"));
    }

    @Test
    public void getTimeFromMinutesTest(){
        assertEquals("00:50",getTimeFromMinutes(50));
        assertEquals("02:10",getTimeFromMinutes(130));
        assertEquals("12:10",getTimeFromMinutes(730));
    }
}
