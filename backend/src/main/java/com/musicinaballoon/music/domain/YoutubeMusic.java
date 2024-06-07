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
@Entity(name = "youtube_music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YoutubeMusic extends StreamingMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "youtube_id", unique = true, nullable = false)
    private String youtubeId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Builder
    public YoutubeMusic(@NonNull String youtubeId, @NonNull String title, String thumbnailUrl) {
        this.youtubeId = youtubeId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String getAlbumImageUrl() {
        return thumbnailUrl;
    }

    @Override
    public String getMusicUrl() {
        return "https://music.youtube.com/watch?v=" + youtubeId;
    }
}
