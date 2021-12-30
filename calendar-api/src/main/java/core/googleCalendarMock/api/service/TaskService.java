package core.googleCalendarMock.api.service;

import com.googleCalendarMock.core.domain.entity.Schedule;
import com.googleCalendarMock.core.domain.entity.repository.ScheduleRepository;
import com.googleCalendarMock.core.service.UserService;
import core.googleCalendarMock.api.dto.AuthUser;
import core.googleCalendarMock.api.dto.TaskCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.task(taskCreateReq.getTitle(), taskCreateReq.getDescription(), taskCreateReq.getTaskAt(), userService.findByUserId(authUser.getId()));
        scheduleRepository.save(taskSchedule);
    }
}
