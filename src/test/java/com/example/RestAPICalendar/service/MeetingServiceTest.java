package com.example.RestAPICalendar.service;

import com.example.RestAPICalendar.dao.MeetingDAO;
import com.example.RestAPICalendar.entity.Meeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestPropertySource("/application.properties")
@SpringBootTest
@Transactional
public class MeetingServiceTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MeetingDAO meetingDAO;

    @Test
    public void saveMeetingTest(){
        Meeting meetingOne = new Meeting();
        meetingOne.setStart("09:00");
        meetingOne.setEnd("10:00");
        entityManager.persist(meetingOne);
        entityManager.flush();
        Meeting meetingTwo = new Meeting();
        meetingTwo.setStart("11:00");
        meetingTwo.setEnd("12:00");
        entityManager.persist(meetingTwo);
        entityManager.flush();

        Iterable<Meeting> iterableMeetings = meetingDAO.findAll();

        List<Meeting> meetings = new ArrayList<>();

        for(Meeting tempMeeting : iterableMeetings) {
            meetings.add(tempMeeting);
        }

        assertEquals(2,meetings.size());
    }
}
