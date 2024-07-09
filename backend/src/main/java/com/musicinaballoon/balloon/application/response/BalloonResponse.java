package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.application.response.SpotifyMusicResponse;
import com.musicinaballoon.music.application.response.YoutubeMusicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record BalloonResponse(
        Long id,

        @Schema(example = "Super Shy")
        String title,
        @Schema(example = "I love this song 🥰")
        String message,
        @Schema(example = "#F06292")
        String colorCode,

        @Schema(example = "youtube")
        String uploadedStreamingMusicType,
        @Schema(example = "https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg")
        String albumImageUrl,
        YoutubeMusicResponse youtubeMusic,
        SpotifyMusicResponse spotifyMusic,

        @Schema(example = "29.9794559943191")
        BigDecimal baseLon,
        @Schema(example = "31.1342812890919")
        BigDecimal baseLat,
        @Schema(example = "2024-05-01T18:04:34.53997Z")
        ZonedDateTime basedAt,

        @Schema(example = "2024-05-01T18:04:34.53997Z")
        ZonedDateTime createdAt,
        @Schema(example = "2024-05-01T18:04:34.53997Z")
        ZonedDateTime updatedAt
) {

    public static BalloonResponse from(Balloon balloon) {
        YoutubeMusicResponse youtubeMusicResponse = getYoutubeMusicResponse(balloon);
        SpotifyMusicResponse spotifyMusicResponse = getSpotifyMusicResponse(balloon);

        return BalloonResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())
                .message(balloon.getMessage())
                .colorCode(balloon.getColorCode())

                .uploadedStreamingMusicType(balloon.getUploadedStreamingMusicType().name())
                .albumImageUrl(balloon.getAlbumImageUrl())
                .youtubeMusic(youtubeMusicResponse)
                .spotifyMusic(spotifyMusicResponse)

                .baseLon(balloon.getBaseLongitude())
                .baseLat(balloon.getBaseLatitude())
                .basedAt(balloon.getBasedAt())

                .createdAt(balloon.getCreatedAt())
                .updatedAt(balloon.getUpdatedAt())
                .build();
    }

    private static YoutubeMusicResponse getYoutubeMusicResponse(Balloon balloon) {
        if (balloon.getYoutubeMusic() == null) {
            return null;
        }

        return YoutubeMusicResponse.from(balloon.getYoutubeMusic());
    }

    private static SpotifyMusicResponse getSpotifyMusicResponse(Balloon balloon) {
        if (balloon.getSpotifyMusic() == null) {
            return null;
        }

        return SpotifyMusicResponse.from(balloon.getSpotifyMusic());
    }
}
