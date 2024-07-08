package com.musicinaballoon.music.application.response;

import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusicType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyMusicResponse extends MusicResponse {

    private String spotifyId;

    @Builder
    public SpotifyMusicResponse(String title, String albumImageUrl, String url,
            String spotifyId) {
        super(title, albumImageUrl, url, StreamingMusicType.SPOTIFY_MUSIC);
        this.spotifyId = spotifyId;
    }

    public static MusicResponse from(SpotifyMusic spotifyMusic) {
        return SpotifyMusicResponse.builder()
                .title(spotifyMusic.getTitle())
                .albumImageUrl(spotifyMusic.getAlbumImageUrl())
                .url(spotifyMusic.getMusicUrl())
                .spotifyId(spotifyMusic.getSpotifyId())
                .build();
    }

    @Schema(example = "5sdQOyqq2IDhvmx2lHOpwd")
    public String getSpotifyId() {
        return spotifyId;
    }

    @Override
    @Schema(example = "SPOTIFY_MUSIC")
    public StreamingMusicType getStreamingType() {
        return super.getStreamingType();
    }

    @Override
    @Schema(example = "https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af")
    public String getAlbumImageUrl() {
        return super.getAlbumImageUrl();
    }

    @Override
    @Schema(example = "Super Shy")
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    @Schema(example = "https://open.spotify.com/track/5sdQOyqq2IDhvmx2lHOpwd")
    public String getUrl() {
        return super.getUrl();
    }
}
