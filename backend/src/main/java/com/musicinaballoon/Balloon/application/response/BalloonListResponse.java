package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import java.util.List;
import lombok.Builder;

@Builder
public record BalloonListResponse(
        List<BalloonResponse> balloons
) {

    public static BalloonListResponse from(List<Balloon> balloons) {
        return BalloonListResponse.builder()
                .balloons(balloons.stream().map(BalloonResponse::from).toList())
                .build();
    }
}
