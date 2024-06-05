package com.musicinaballoon.Balloon;

import com.musicinaballoon.music.streaming.StreamingMusic;
import com.musicinaballoon.music.streaming.StreamingMusicType;
import com.musicinaballoon.music.streaming.spotify.SpotifyMusic;
import com.musicinaballoon.music.streaming.youtube.YoutubeMusic;
import com.musicinaballoon.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity(name = "balloon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balloon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "uploaded_streaming_music_type", nullable = false)
    private StreamingMusicType uploadedStreamingMusicType;

    @JoinColumn(name = "youtube_music_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private YoutubeMusic youtubeMusic;

    @JoinColumn(name = "spotify_music_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SpotifyMusic spotifyMusic;

    @JoinColumn(name = "creator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @Column(name = "latitude", precision = 16, scale = 13, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 16, scale = 13, nullable = false)
    private BigDecimal longitude;

    @Builder
    public Balloon(
            @NonNull StreamingMusicType uploadedStreamingMusicType,
            YoutubeMusic youtubeMusic,
            SpotifyMusic spotifyMusic,
            @NonNull User creator,
            @NonNull BigDecimal latitude,
            @NonNull BigDecimal longitude
    ) {
        this.uploadedStreamingMusicType = uploadedStreamingMusicType;
        this.youtubeMusic = youtubeMusic;
        this.spotifyMusic = spotifyMusic;
        this.creator = creator;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMusicTitle() {
        return getUploadedStreamingMusic().getTitle();
    }

    public String getAlbumImageUrl() {
        return getUploadedStreamingMusic().getAlbumImageUrl();
    }

    private StreamingMusic getUploadedStreamingMusic() {
        return switch (uploadedStreamingMusicType) {
            case StreamingMusicType.SPOTIFY_MUSIC -> spotifyMusic;
            case StreamingMusicType.YOUTUBE_MUSIC -> youtubeMusic;
        };
    }
}
