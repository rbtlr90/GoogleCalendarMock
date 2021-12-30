package com.googleCalendarMock.core.domain.entity;

import com.googleCalendarMock.core.domain.Event;
import com.googleCalendarMock.core.domain.RequestStatus;
import com.googleCalendarMock.core.domain.ScheduleType;
import com.googleCalendarMock.core.util.Period;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name = "engagements")
public class Engagement extends BaseEntity {

    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    public Engagement(Schedule eventSchedule, User attendee) {
        assert eventSchedule.getScheduleType() == ScheduleType.EVENT;
        this.schedule = eventSchedule;
        this.status = RequestStatus.REQUESTED;
        this.attendee = attendee;
    }

    public Event getEvent() {
        return schedule.toEvent();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public User getAttendee() {
        return attendee;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public boolean isOverlapped(Period period) {
        return this.schedule.isOverlapped(period);
    }
}
