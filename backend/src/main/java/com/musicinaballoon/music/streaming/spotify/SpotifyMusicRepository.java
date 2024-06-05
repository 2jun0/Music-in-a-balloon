package com.musicinaballoon.music.streaming.spotify;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyMusicRepository extends JpaRepository<SpotifyMusic, Long> {
    Optional<SpotifyMusic> findBySpotifyId(String spotifyId);
}
