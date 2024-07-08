package com.musicinaballoon.music.application.response;

import com.musicinaballoon.music.domain.StreamingMusic;
import lombok.Builder;

@Builder
public record MusicResponse(
        String title,
        String albumImageUrl,
        String url
) {

    public static MusicResponse from(StreamingMusic music) {
        return MusicResponse.builder()
                .title(music.getTitle())
                .albumImageUrl(music.getAlbumImageUrl())
                .url(music.getMusicUrl())
                .build();
    }
}
