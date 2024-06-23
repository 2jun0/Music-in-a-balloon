package com.musicinaballoon.music.external.response;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;

public record YoutubeVideo(String id, String title, String description, YoutubeThumbnail thumbnail) {

    public static YoutubeVideo from(Video video) {
        VideoSnippet videoSnippet = video.getSnippet();
        return new YoutubeVideo(video.getId(), videoSnippet.getTitle(),
                videoSnippet.getDescription(), YoutubeThumbnail.from(videoSnippet.getThumbnails()));
    }
}
