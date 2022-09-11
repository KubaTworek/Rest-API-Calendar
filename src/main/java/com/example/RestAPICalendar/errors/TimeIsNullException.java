package com.example.RestAPICalendar.errors;

public class TimeIsNullException extends RuntimeException {
    public TimeIsNullException(String message) {
        super(message);
    }
}
