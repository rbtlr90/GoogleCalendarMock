package core.googleCalendarMock.api.service;

import com.googleCalendarMock.core.domain.entity.Schedule;
import com.googleCalendarMock.core.domain.entity.User;
import com.googleCalendarMock.core.domain.entity.repository.ScheduleRepository;
import com.googleCalendarMock.core.service.UserService;
import core.googleCalendarMock.api.dto.AuthUser;
import core.googleCalendarMock.api.dto.NotificationCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());
        final List<LocalDateTime> notifyAtList= notificationCreateReq.getRepeatTimes();
        notifyAtList.forEach(notifyAt -> {
            final Schedule notificationSchedule =
                    Schedule.notification(
                            notificationCreateReq.getTitle(),
                            notificationCreateReq.getNotifyAt(),
                            user
                    );
            scheduleRepository.save(notificationSchedule);
        });
    }
}
