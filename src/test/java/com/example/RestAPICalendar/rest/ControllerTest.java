package com.example.RestAPICalendar.rest;

import com.example.RestAPICalendar.entity.Calendar;
import com.example.RestAPICalendar.entity.Working;
import com.example.RestAPICalendar.service.CalendarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@Transactional
public class ControllerTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

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
    public void getCalendarsTest() throws Exception {
        Calendar calendar = new Calendar();
        Working workingTime = new Working();
        workingTime.setId(0);
        workingTime.setStart("08:30");
        workingTime.setEnd("16:30");
        workingTime.setCalendar(calendar);
        entityManager.persist(workingTime);
        calendarService.save(calendar);

        MvcResult mvcResult = mockMvc.perform(get("/calendars"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void saveCalendarTest() throws Exception {
        /*Calendar calendar = new Calendar();
        calendar.setId(10);
        Working workingTime = new Working();
        Meeting meeting = new Meeting();
        workingTime.setId(0);
        workingTime.setStart("08:30");
        workingTime.setEnd("16:30");
        meeting.setId(0);
        meeting.setStart("09:00");
        meeting.setEnd("10:00");
        calendar.setWorkingTime(workingTime);
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting);
        calendar.setMeetings(meetings);
        entityManager.persist(workingTime);

        MvcResult mvcResult = mockMvc.perform(post("/calendars/" + calendar))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();*/
    }

    @Test
    public void getMeetingsTest(){

    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("DELETE FROM Meeting");
        jdbc.execute("DELETE FROM Calendar");
        jdbc.execute("DELETE FROM Working");
    }
}
