package com.musicinaballoon.music.application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetMusicParam(
        @NotBlank
        @Size(max = 255)
        @Schema(example = "https://music.youtube.com/watch?v=n7ePZLn9_lQ")
        String streamingUrl
) {
}
