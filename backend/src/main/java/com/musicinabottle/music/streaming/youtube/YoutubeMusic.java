package com.musicinabottle.music.streaming.youtube;

import com.musicinabottle.music.streaming.StreamingMusic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity(name = "youtube_music")
public class YoutubeMusic implements StreamingMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "youtube_id", unique = true, nullable = false)
    private String youtubeId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @Override
    public String getAlbumImageUrl() {
        return "";
    }

    @Override
    public String getMusicUrl() {
        return "";
    }
}
