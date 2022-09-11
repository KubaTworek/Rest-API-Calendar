package com.example.RestAPICalendar.dao;


import com.example.RestAPICalendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarDAO extends JpaRepository<Calendar, Integer> {
}
