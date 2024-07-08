package com.musicinaballoon.music.application.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.musicinaballoon.music.domain.StreamingMusicType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotifyMusicResponse.class),
        @JsonSubTypes.Type(value = YoutubeMusicResponse.class),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MusicResponse {
    protected String title;
    protected String albumImageUrl;
    protected String url;
    protected StreamingMusicType streamingType;

    public MusicResponse(String title, String albumImageUrl, String url, StreamingMusicType streamingType) {
        this.title = title;
        this.albumImageUrl = albumImageUrl;
        this.url = url;
        this.streamingType = streamingType;
    }
}
