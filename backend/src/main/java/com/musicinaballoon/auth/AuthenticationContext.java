package com.musicinaballoon.auth;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationContext {

    private Long userId = null;

    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }
}
