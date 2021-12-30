package core.googleCalendarMock.api.controller;

import core.googleCalendarMock.api.dto.LoginReq;
import core.googleCalendarMock.api.dto.SignUpReq;
import core.googleCalendarMock.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpReq signUpReq, HttpSession session) {
        loginService.signUp(signUpReq, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<Void> login(@RequestBody LoginReq loginReq, HttpSession session) {
        loginService.login(loginReq, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/logout")
    private ResponseEntity<Void> logout(HttpSession session) {
        loginService.logout(session);
        return ResponseEntity.ok().build();
    }
}
