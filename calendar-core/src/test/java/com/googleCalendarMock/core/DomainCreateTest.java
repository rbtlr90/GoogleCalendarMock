package com.googleCalendarMock.core;

import com.googleCalendarMock.core.domain.ScheduleType;
import com.googleCalendarMock.core.domain.entity.Engagement;
import com.googleCalendarMock.core.domain.Event;
import com.googleCalendarMock.core.domain.RequestStatus;
import com.googleCalendarMock.core.domain.entity.Schedule;
import com.googleCalendarMock.core.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User me = new User("meme", "email", "pw", LocalDate.now());
        final Schedule taskSchedule = Schedule.task("할일", "청소하기", LocalDateTime.now(), me);
        assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskSchedule.toTask().getTitle(), "할일");
    }
}
