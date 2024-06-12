package com.musicinaballoon.user.application.response;

import com.musicinaballoon.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .build();
    }
}
