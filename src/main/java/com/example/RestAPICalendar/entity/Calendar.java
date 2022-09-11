package com.example.RestAPICalendar.entity;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Calendar")
@Component
@NoArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.ALL})
    @JoinColumn(name = "working_id")
    private Working workingTime;

    @OneToMany(mappedBy = "Calendar", cascade = { CascadeType.ALL })
    private List<Meeting> meetings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Working getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Working workingTime) {
        this.workingTime = workingTime;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public void add(Meeting tempMeeting) {
        if(meetings == null) {
            meetings = new ArrayList<>();
        }

        meetings.add(tempMeeting);
        tempMeeting.setCalendar(this);
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", workingTime=" + workingTime +
                ", meetings=" + meetings +
                '}';
    }
}
