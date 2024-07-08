package com.musicinaballoon.music.application.response;

import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YoutubeMusicResponse extends MusicResponse {

    private String youtubeId;
    private String youtubeUrl;

    @Builder
    public YoutubeMusicResponse(String title, String albumImageUrl, String url,
            String youtubeId, String youtubeUrl) {
        super(title, albumImageUrl, url, StreamingMusicType.YOUTUBE_MUSIC);
        this.youtubeId = youtubeId;
        this.youtubeUrl = youtubeUrl;
    }

    public static MusicResponse from(YoutubeMusic youtubeMusic) {
        return YoutubeMusicResponse.builder()
                .title(youtubeMusic.getTitle())
                .albumImageUrl(youtubeMusic.getAlbumImageUrl())
                .url(youtubeMusic.getMusicUrl())
                .youtubeId(youtubeMusic.getYoutubeId())
                .youtubeUrl(youtubeMusic.getYoutubeUrl())
                .build();
    }

    @Schema(example = "https://youtu.be/n7ePZLn9_lQ")
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    @Schema(example = "n7ePZLn9_lQ")
    public String getYoutubeId() {
        return youtubeId;
    }

    @Override
    @Schema(example = "YOUTUBE_MUSIC")
    public StreamingMusicType getStreamingType() {
        return super.getStreamingType();
    }

    @Override
    @Schema(example = "Super Shy")
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    @Schema(example = "https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg")
    public String getAlbumImageUrl() {
        return super.getAlbumImageUrl();
    }

    @Override
    @Schema(example = "https://music.youtube.com/watch?v=n7ePZLn9_lQ")
    public String getUrl() {
        return super.getUrl();
    }
}
