package com.musicinaballoon.music.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity(name = "spotify_music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyMusic extends StreamingMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spotify_id", nullable = false, unique = true)
    private String spotifyId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "album_image_url")
    private String albumImageUrl;

    @Builder
    public SpotifyMusic(Long id, @NonNull String spotifyId, @NonNull String title, String albumImageUrl) {
        this.id = id;
        this.spotifyId = spotifyId;
        this.title = title;
        this.albumImageUrl = albumImageUrl;
    }

    @Override
    public String getMusicUrl() {
        return "https://open.spotify.com/track/" + spotifyId;
    }

    @Override
    public StreamingMusicType getStreamingMusicType() {
        return StreamingMusicType.SPOTIFY_MUSIC;
    }
}
