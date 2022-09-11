package com.example.RestAPICalendar.errors;

public class CalendarNotFoundException extends RuntimeException{
    public CalendarNotFoundException(String message) {
        super(message);
    }
}
