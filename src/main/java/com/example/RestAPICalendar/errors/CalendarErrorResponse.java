package com.example.RestAPICalendar.errors;

public class CalendarErrorResponse {
    private int status;
    private String message;

    public CalendarErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
