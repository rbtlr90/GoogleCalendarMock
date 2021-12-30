package core.googleCalendarMock.api.service;

import com.googleCalendarMock.core.domain.RequestStatus;
import com.googleCalendarMock.core.domain.entity.Engagement;
import com.googleCalendarMock.core.domain.entity.Schedule;
import com.googleCalendarMock.core.domain.entity.User;
import com.googleCalendarMock.core.domain.entity.repository.EngagementRepository;
import com.googleCalendarMock.core.domain.entity.repository.ScheduleRepository;
import com.googleCalendarMock.core.exception.CalendarException;
import com.googleCalendarMock.core.exception.ErrorCode;
import com.googleCalendarMock.core.service.UserService;
import core.googleCalendarMock.api.dto.AuthUser;
import core.googleCalendarMock.api.dto.EventCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;
    private final EmailService emailService;

    @Transactional
    public void create(EventCreateReq req, AuthUser authUser) {
        final List<Engagement> engagementList =
                engagementRepository.findAllByAttendeeIdInAndSchedule_EndAtAfter(req.getAttendeeIds(),
                        req.getStartAt());
        if (engagementList
                .stream()
                .anyMatch(e -> e.getEvent().isOverlapped(req.getStartAt(), req.getEndAt())
                        && e.getStatus() == RequestStatus.ACCEPTED)) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(req.getTitle(), req.getDescription(), req.getStartAt(), req.getEndAt(), userService.getOrThrowById(authUser.getId()));
        scheduleRepository.save(eventSchedule);
        req.getAttendeeIds().stream()
                .map(userService::getOrThrowById)
                .forEach(user -> {
                    final Engagement e = engagementRepository.save(new Engagement(eventSchedule, user));
                    emailService.sendEngagement(e);
                });
    }
}
