package com.musicinaballoon.music.streaming.youtube;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeMusicRepository extends JpaRepository<YoutubeMusic, Long> {
    Optional<YoutubeMusic> findByYoutubeId(String youtubeId);
}
