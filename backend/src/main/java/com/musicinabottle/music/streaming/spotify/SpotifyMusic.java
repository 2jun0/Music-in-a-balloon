package com.musicinabottle.music.streaming.spotify;

import com.musicinabottle.music.streaming.StreamingMusic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
public class SpotifyMusic implements StreamingMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spotify_id", nullable = false, unique = true)
    private String spotifyId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "album_image_url")
    private String albumImageUrl;

    @Override
    public String getMusicUrl() {
        return "https://api.spotify.com/v1/tracks/" + spotifyId;
    }
}
