package com.musicinabottle.user.response;

import com.musicinabottle.user.User;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record UserResponse(Long id) {
    public static UserResponse of(@NonNull User user) {
        return UserResponse.builder()
                .id(user.getId())
                .build();
    }
}
