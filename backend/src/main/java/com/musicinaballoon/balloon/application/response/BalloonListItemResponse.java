package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record BalloonListItemResponse(
        Long id,

        @Schema(example = "Super Shy")
        String title,

        @Schema(example = "2024-06-27T07:42:02.872Z")
        ZonedDateTime createdAt
) {

    public static BalloonListItemResponse from(Balloon balloon) {
        return BalloonListItemResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())
                .createdAt(balloon.getCreatedAt())
                .build();
    }
}
