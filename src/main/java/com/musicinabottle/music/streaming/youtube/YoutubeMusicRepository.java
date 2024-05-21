package com.musicinabottle.music.streaming.youtube;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeMusicRepository extends JpaRepository<YoutubeMusic, Long> {
    Optional<YoutubeMusic> findByYoutubeId(String youtubeId);
}
