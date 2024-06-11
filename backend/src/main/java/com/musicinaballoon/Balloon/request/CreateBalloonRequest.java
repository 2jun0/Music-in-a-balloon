package com.musicinaballoon.balloon.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBalloonRequest(
        @NotBlank
        @Schema(example = "https://music.youtube.com/watch?v=n7ePZLn9_lQ")
        String streamingMusicUrl,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        @Schema(example = "29.97945599431905")
        BigDecimal latitude,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        @Schema(example = "31.134281289091934")
        BigDecimal longitude
) {
}
