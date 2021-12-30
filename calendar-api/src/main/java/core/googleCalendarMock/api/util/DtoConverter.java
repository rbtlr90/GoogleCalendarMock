package core.googleCalendarMock.api.util;

import com.googleCalendarMock.core.domain.entity.Schedule;
import com.googleCalendarMock.core.exception.CalendarException;
import com.googleCalendarMock.core.exception.ErrorCode;
import core.googleCalendarMock.api.dto.EventDto;
import core.googleCalendarMock.api.dto.NotificationDto;
import core.googleCalendarMock.api.dto.ScheduleDto;
import core.googleCalendarMock.api.dto.TaskDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public abstract class DtoConverter {

    public static ScheduleDto fromSchedule(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case EVENT:
                return EventDto.builder()
                        .scheduleId(schedule.getId())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return TaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return NotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }
}
