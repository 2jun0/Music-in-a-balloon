package com.musicinaballoon.balloon.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
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
        Double curLon,
        Double curLat,
        ZonedDateTime createdAt) {

    public static BalloonResponse from(Balloon balloon, Double curLon, Double curLat) {
        return BalloonResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())
                .uploadedStreamingMusicType(balloon.getUploadedStreamingMusicType().name())
                .albumImageUrl(balloon.getAlbumImageUrl())
                .baseLon(balloon.getBaseLon())
                .baseLat(balloon.getBaseLat())
                .curLat(curLat)
                .curLon(curLon)
                .createdAt(balloon.getCreated_at())
                .build();
    }

    public static BalloonResponse from(Balloon balloon) {
        return BalloonResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())
                .uploadedStreamingMusicType(balloon.getUploadedStreamingMusicType().name())
                .albumImageUrl(balloon.getAlbumImageUrl())
                .baseLon(balloon.getBaseLon())
                .baseLat(balloon.getBaseLat())
                .createdAt(balloon.getCreated_at())
                .build();
    }
}
