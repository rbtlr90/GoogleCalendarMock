package core.googleCalendarMock.api.service;

import com.googleCalendarMock.core.domain.entity.User;
import com.googleCalendarMock.core.dto.UserCreateReq;
import com.googleCalendarMock.core.exception.CalendarException;
import com.googleCalendarMock.core.exception.ErrorCode;
import com.googleCalendarMock.core.service.UserService;
import core.googleCalendarMock.api.dto.LoginReq;
import core.googleCalendarMock.api.dto.SignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    public void signUp(SignUpReq signUpReq, HttpSession session) {
        final User user = userService.create(new UserCreateReq(
                signUpReq.getName(),
                signUpReq.getEmail(),
                signUpReq.getPassword(),
                signUpReq.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_KEY, user.getId());
    }

    public void login(LoginReq loginReq, HttpSession session) {
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId != null) {
            return ;
        }
        final Optional<User> user =
                userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());
        if (user.isPresent()) {
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        } else {
            throw new CalendarException(ErrorCode.PASSWORD_NOT_MATCH);
        }

    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_KEY);
    }
}
