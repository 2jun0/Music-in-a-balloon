package com.musicinaballoon.balloon.application.request;

import com.musicinaballoon.balloon.domain.BalloonReactionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReactBalloonRequest(
        @Schema(example = "BALLOON")
        BalloonReactionType balloonReactionType
) {
}
