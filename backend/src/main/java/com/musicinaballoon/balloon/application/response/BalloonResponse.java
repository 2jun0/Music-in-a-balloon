package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.application.response.MusicResponse;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record BalloonResponse(
        Long id,
        String title,
        String uploadedStreamingMusicType,
        String albumImageUrl,
        BigDecimal baseLon,
        BigDecimal baseLat,
        String message,
        MusicResponse youtubeMusic,
        MusicResponse spotifyMusic,
        ZonedDateTime createdAt) {

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
