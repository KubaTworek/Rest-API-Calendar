package com.example.RestAPICalendar.model;

public class MeetTime {
    private String start;
    private String end;

    public MeetTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "MeetTimes{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
