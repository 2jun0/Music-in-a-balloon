package com.musicinaballoon.balloon.application.request;

import com.musicinaballoon.balloon.domain.BalloonReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReactBalloonRequest(
        @NotNull
        @Schema(example = "BALLOON")
        BalloonReactionType balloonReactionType
) {
}
