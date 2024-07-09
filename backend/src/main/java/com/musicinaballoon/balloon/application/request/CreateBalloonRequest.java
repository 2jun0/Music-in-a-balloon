package com.musicinaballoon.balloon.application.request;

import com.musicinaballoon.balloon.annotation.ColorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateBalloonRequest(
        @NotBlank
        @Size(max = 255)
        @Schema(example = "https://music.youtube.com/watch?v=n7ePZLn9_lQ")
        String streamingMusicUrl,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        @Schema(example = "29.97945599431905")
        BigDecimal latitude,

        @NotNull
        @Digits(integer = 3, fraction = 13)
        @Schema(example = "31.134281289091934")
        BigDecimal longitude,

        @NotBlank
        @Size(max = 255)
        @Schema(example = "My favorite music 🎧")
        String message,

        @NotNull
        @ColorCode
        @Schema(example = "#F06292")
        String colorCode
) {
}
