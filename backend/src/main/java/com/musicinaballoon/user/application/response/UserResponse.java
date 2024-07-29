package com.musicinaballoon.user.application.response;

import com.musicinaballoon.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String name) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
