package com.musicinaballoon.user.response;

import com.musicinaballoon.user.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .build();
    }
}
