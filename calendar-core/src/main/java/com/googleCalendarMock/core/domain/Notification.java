package com.googleCalendarMock.core.domain;

import com.googleCalendarMock.core.domain.entity.Schedule;
import com.googleCalendarMock.core.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Notification {

    private Schedule schedule;

    public Notification(Schedule schedule) {
        this.schedule = schedule;
    }
}
