package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record BalloonListItemResponse(
        Long id,

        @Schema(example = "Super Shy")
        String title,

        @Schema(example = "29.9794559943191")
        BigDecimal baseLon,
        @Schema(example = "31.1342812890919")
        BigDecimal baseLat,
        @Schema(example = "2024-06-27T07:42:02.872Z")
        ZonedDateTime basedAt,

        @Schema(example = "2024-06-27T07:42:02.872Z")
        ZonedDateTime createdAt,
        @Schema(example = "2024-06-27T07:42:02.872Z")
        ZonedDateTime updatedAt
) {

    public static BalloonListItemResponse from(Balloon balloon) {
        return BalloonListItemResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())

                .baseLat(balloon.getBaseLat())
                .baseLon(balloon.getBaseLon())
                .basedAt(balloon.getBasedAt())

                .createdAt(balloon.getCreatedAt())
                .updatedAt(balloon.getUpdatedAt())
                .build();
    }
}
