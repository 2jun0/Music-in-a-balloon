package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.application.response.MusicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record BalloonResponse(
        Long id,

        @Schema(example = "Super Shy")
        String title,

        @Schema(example = "youtube")
        String uploadedStreamingMusicType,

        @Schema(example = "https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg")
        String albumImageUrl,

        @Schema(example = "29.9794559943191")
        BigDecimal baseLon,

        @Schema(example = "31.1342812890919")
        BigDecimal baseLat,

        @Schema(example = "I love this song 🥰")
        String message,

        MusicResponse youtubeMusic,

        MusicResponse spotifyMusic,

        @Schema(example = "2024-05-01T18:04:34.53997Z")
        ZonedDateTime createdAt,

        @Schema(example = "2024-05-01T18:04:34.53997Z")
        ZonedDateTime updatedAt
) {

    public static BalloonResponse from(Balloon balloon) {
        MusicResponse youtubeMusicResponse = getYoutubeMusicResponse(balloon);
        MusicResponse spotifyMusicResponse = getSpotifyMusicResponse(balloon);

        return BalloonResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())
                .uploadedStreamingMusicType(balloon.getUploadedStreamingMusicType().name())
                .albumImageUrl(balloon.getAlbumImageUrl())
                .baseLon(balloon.getBaseLon())
                .baseLat(balloon.getBaseLat())
                .message(balloon.getMessage())
                .youtubeMusic(youtubeMusicResponse)
                .spotifyMusic(spotifyMusicResponse)
                .createdAt(balloon.getCreatedAt())
                .updatedAt(balloon.getUpdatedAt())
                .build();
    }

    private static MusicResponse getYoutubeMusicResponse(Balloon balloon) {
        if (balloon.getYoutubeMusic() == null) {
            return null;
        }

        return MusicResponse.from(balloon.getYoutubeMusic());
    }

    private static MusicResponse getSpotifyMusicResponse(Balloon balloon) {
        if (balloon.getSpotifyMusic() == null) {
            return null;
        }

        return MusicResponse.from(balloon.getSpotifyMusic());
    }
}
