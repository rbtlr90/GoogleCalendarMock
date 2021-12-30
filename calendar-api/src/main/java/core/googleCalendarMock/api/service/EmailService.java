package core.googleCalendarMock.api.service;

import com.googleCalendarMock.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement e);
}
