package com.musicinabottle.bottle;

import com.musicinabottle.music.streaming.StreamingMusic;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.spotify.SpotifyMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
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
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity(name = "bottle")
public class Bottle {
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

    public String getMusicTitle() {
        return getUploadedStreamingMusic().getMusicUrl();
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
