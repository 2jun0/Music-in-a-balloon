package com.musicinaballoon.music.repository;

import com.musicinaballoon.music.domain.SpotifyMusic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyMusicRepository extends JpaRepository<SpotifyMusic, Long> {
    Optional<SpotifyMusic> findBySpotifyId(String spotifyId);
}
