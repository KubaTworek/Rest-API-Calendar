package com.example.RestAPICalendar.errors;

public class WorkingTimeIsNullException extends RuntimeException {
    public WorkingTimeIsNullException(String message) {
        super(message);
    }
}
