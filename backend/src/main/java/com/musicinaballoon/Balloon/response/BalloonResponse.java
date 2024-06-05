package com.musicinaballoon.Balloon.response;

import com.musicinaballoon.Balloon.Balloon;
import lombok.Builder;

@Builder
public record BalloonResponse(Long id, String title, String uploadedStreamingMusicType, String albumImageUrl) {

    public static BalloonResponse of(Balloon balloon) {
        return BalloonResponse.builder()
                .id(balloon.getId())
                .title(balloon.getMusicTitle())
                .uploadedStreamingMusicType(balloon.getUploadedStreamingMusicType().name())
                .albumImageUrl(balloon.getAlbumImageUrl())
                .build();
    }
}
