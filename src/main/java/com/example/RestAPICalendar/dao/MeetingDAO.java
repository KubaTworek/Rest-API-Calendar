package com.example.RestAPICalendar.dao;

import com.example.RestAPICalendar.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingDAO extends JpaRepository<Meeting, Integer> {
}
