package com.googleCalendarMock.core.service;

import com.googleCalendarMock.core.domain.entity.User;
import com.googleCalendarMock.core.domain.entity.repository.UserRepository;
import com.googleCalendarMock.core.dto.UserCreateReq;
import com.googleCalendarMock.core.exception.CalendarException;
import com.googleCalendarMock.core.exception.ErrorCode;
import com.googleCalendarMock.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq userCreateReq) {
        userRepository.findByEmail(userCreateReq.getEmail())
                .ifPresent( u -> {
                    throw new CalendarException(ErrorCode.ALREADY_EXISTS_USER);
                });
        return userRepository.save(new User(
                userCreateReq.getName(),
                userCreateReq.getEmail(),
                userCreateReq.getPassword(),
                userCreateReq.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user : null);
    }

    @Transactional
    public User findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CalendarException(ErrorCode.USER_NOT_FOUND));
    }

    public User getOrThrowById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("no user."));
    }
}
