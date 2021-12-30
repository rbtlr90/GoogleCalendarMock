package core.googleCalendarMock.api.dto;

import com.googleCalendarMock.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDto implements ScheduleDto{

    private final Long scheduleId;
    private final Long writerId;
    private final String title;
    private final LocalDateTime notifyAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}
