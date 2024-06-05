package com.musicinaballoon.user;

import com.musicinaballoon.auth.UserId;
import com.musicinaballoon.user.request.CreateUserRequest;
import com.musicinaballoon.user.response.UserResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/user/me")
    public UserResponse getMe(@UserId Long userId) {
        return userFacade.getMe(userId);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/user")
    public void createUser(HttpServletResponse response, @Valid @RequestBody CreateUserRequest createUser) {
        UserResponse user = userFacade.createUser(createUser);
        Cookie cookie = new Cookie("userId", String.valueOf(user.id()));
        response.addCookie(cookie);
    }
}
