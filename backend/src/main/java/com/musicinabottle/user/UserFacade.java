package com.musicinabottle.user;

import com.musicinabottle.user.request.CreateUserRequest;
import com.musicinabottle.user.response.UserResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserResponse getMe(@NonNull Long userId) {
        User user = userService.getUser(userId);
        return UserResponse.of(user);
    }

    public UserResponse createUser(@NonNull CreateUser createUser) {
        User user = userService.createUser(createUser.username());
        return UserResponse.of(user);
    }
}
