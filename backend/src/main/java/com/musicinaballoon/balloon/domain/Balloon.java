package com.musicinaballoon.balloon.domain;

import static com.musicinaballoon.common.util.TimeUtil.secondTimeDifference;
import static com.musicinaballoon.common.util.TimeUtil.utcNow;

import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.geolocation.domain.Geolocation;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusic;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.domain.Wave;
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
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity(name = "balloon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balloon extends BaseEntity {

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

    @JoinColumn(name = "creator_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @Column(name = "base_latitude", precision = 16, scale = 13, nullable = false)
    private BigDecimal baseLatitude;

    @Column(name = "base_longitude", precision = 16, scale = 13, nullable = false)
    private BigDecimal baseLongitude;

    @Column(name = "message", length = 255, nullable = false)
    private String message;

    @Column(name = "based_at", nullable = false)
    private ZonedDateTime basedAt;

    @Builder
    public Balloon(
            @NonNull StreamingMusicType uploadedStreamingMusicType,
            YoutubeMusic youtubeMusic,
            SpotifyMusic spotifyMusic,
            @NonNull User creator,
            @NonNull BigDecimal baseLatitude,
            @NonNull BigDecimal baseLongitude,
            @NonNull String message
    ) {
        this.uploadedStreamingMusicType = uploadedStreamingMusicType;
        this.youtubeMusic = youtubeMusic;
        this.spotifyMusic = spotifyMusic;
        this.creator = creator;
        this.baseLatitude = baseLatitude;
        this.baseLongitude = baseLongitude;
        this.message = message;
    }

    public Geolocation getCurrentGeolocation(Wave wave) {
        long time = secondTimeDifference(utcNow(), basedAt);
        return wave.calculateGeolocation(baseLatitude, baseLongitude, time);
    }

    @Override
    public void prePersist() {
        super.prePersist();
        this.basedAt = getCreatedAt();
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
