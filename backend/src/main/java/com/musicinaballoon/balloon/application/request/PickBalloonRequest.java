package com.musicinaballoon.balloon.application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PickBalloonRequest(
        @NotNull
        @Digits(integer = 3, fraction = 13)
        @Schema(example = "29.97945599431905")
        BigDecimal userLatitude,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        @Schema(example = "31.134281289091934")
        BigDecimal userLongitude
) {
}
