package com.example.RestAPICalendar.errors;

public class WrongTimeException extends RuntimeException{
    public WrongTimeException(String message) {
        super(message);
    }
}
