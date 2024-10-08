package com.musicinaballoon.user.application;

import com.musicinaballoon.user.application.request.CreateUserRequest;
import com.musicinaballoon.user.application.response.UserResponse;
import com.musicinaballoon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserResponse getMe(Long userId) {
        User user = userService.getUser(userId);
        return UserResponse.from(user);
    }

    public UserResponse createUser(CreateUserRequest createUser) {
        User user = userService.createUser(createUser.username());
        return UserResponse.from(user);
    }
}
