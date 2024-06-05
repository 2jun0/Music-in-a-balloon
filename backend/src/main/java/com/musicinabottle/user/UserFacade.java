package com.musicinabottle.user;

import com.musicinabottle.user.request.CreateUserRequest;
import com.musicinabottle.user.response.UserResponse;
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
        return UserResponse.of(user);
    }

    public UserResponse createUser(CreateUserRequest createUser) {
        User user = userService.createUser(createUser.username());
        return UserResponse.of(user);
    }
}
