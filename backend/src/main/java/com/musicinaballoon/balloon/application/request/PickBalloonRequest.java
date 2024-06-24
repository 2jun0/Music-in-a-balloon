package com.musicinaballoon.balloon.application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record PickBalloonRequest(
        @Size(max = 255)
        @Schema(example = "I love it!")
        String reply
) {
}
