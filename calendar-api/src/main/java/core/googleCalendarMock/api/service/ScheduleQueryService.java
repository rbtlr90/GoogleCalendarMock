package core.googleCalendarMock.api.service;

import com.googleCalendarMock.core.domain.entity.repository.EngagementRepository;
import com.googleCalendarMock.core.domain.entity.repository.ScheduleRepository;
import com.googleCalendarMock.core.util.Period;
import core.googleCalendarMock.api.dto.AuthUser;
import core.googleCalendarMock.api.dto.ScheduleDto;
import core.googleCalendarMock.api.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleQueryService {
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getSchedulesByDay(LocalDate date, AuthUser authUser) {
        final  Period period = Period.of(date, date);
        return getScheduleByPeriod(authUser, period);
    }

    public List<ScheduleDto> getSchedulesByWeek(LocalDate startOfWeek, AuthUser authUser) {
        final Period period = Period.of(startOfWeek, startOfWeek.plusDays(6));
        return getScheduleByPeriod(authUser, period);
    }

    public List<ScheduleDto> getSchedulesByMonth(YearMonth yearMonth, AuthUser authUser) {
        final Period period = Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
        return getScheduleByPeriod(authUser, period);
    }

    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        return Stream.concat(
                scheduleRepository
                        .findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(period))
                        .map(schedule -> DtoConverter.fromSchedule(schedule)),
                engagementRepository
                        .findAllByAttendeeId(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(period))
                        .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()))
        ).collect(toList());
    }
}