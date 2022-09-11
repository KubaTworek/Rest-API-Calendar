package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.entity.Working;
import com.example.RestAPICalendar.errors.CalendarNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestPropertySource("/application.properties")
@SpringBootTest
@Transactional
public class CalendarServiceTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private CalendarService calendarService;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute("DELETE FROM Meeting");
        jdbc.execute("DELETE FROM Calendar");
        jdbc.execute("DELETE FROM Working");
        jdbc.execute("INSERT INTO Working(id, start, end)" + "VALUES (1,'09:00','17:00')");
        jdbc.execute("INSERT INTO Calendar(id, working_id)" + "VALUES (1,1)");
        jdbc.execute("INSERT INTO Meeting(id, start, end, calendar_id)" + "VALUES (1,'09:00','10:00',1)");
    }

    @Test
    public void findAllCalendarsTest(){
        Iterable<Calendar> iterableCalendars = calendarService.findAll();

        List<Calendar> calendars = new ArrayList<>();

        for(Calendar calendar : iterableCalendars) {
            calendars.add(calendar);
        }

        assertEquals(1,calendars.size());
    }

    @Test
    public void findByIdCalendarTest(){
        Calendar calendar = calendarService.findById(1);

        assertEquals(1,calendar.getId());
        assertEquals(1,calendar.getWorkingTime().getId());
        assertEquals(1,calendar.getMeetings().size());
        assertThrows(CalendarNotFoundException.class, () -> calendarService.findById(2));
    }

    @Test
    public void saveCalendarTest(){
        Calendar calendar = new Calendar();
        Working workingTime = new Working();
        workingTime.setId(0);
        workingTime.setStart("08:30");
        workingTime.setEnd("16:30");
        workingTime.setCalendar(calendar);
        entityManager.persist(workingTime);
        entityManager.persist(calendar);
        entityManager.flush();

        Iterable<Calendar> iterableCalendars = calendarService.findAll();
        List<Calendar> calendars = new ArrayList<>();

        for(Calendar tempCalendar : iterableCalendars) {
            calendars.add(tempCalendar);
        }

        assertEquals(2,calendars.size());
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("DELETE FROM Meeting");
        jdbc.execute("DELETE FROM Calendar");
        jdbc.execute("DELETE FROM Working");
    }
}
