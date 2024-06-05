package com.musicinaballoon.balloon.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBalloonRequest(
        @NotBlank
        String streamingMusicUrl,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        BigDecimal latitude,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        BigDecimal longitude
) {
}
