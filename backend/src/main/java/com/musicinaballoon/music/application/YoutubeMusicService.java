package com.musicinaballoon.music.application;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.external.YoutubeApi;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeMusicService {
    private final YoutubeMusicRepository youtubeMusicRepository;
    private final YoutubeApi youtubeApi;

    public YoutubeMusic getYoutubeMusic(String youtubeId) {
        return youtubeMusicRepository.findByYoutubeId(youtubeId)
                .orElseGet(() -> createNewYoutubeMusic(youtubeId));
    }

    private YoutubeMusic createNewYoutubeMusic(String youtubeId) {
        Video video = getYoutubeVideo(youtubeId);
        VideoSnippet videoSnippet = video.getSnippet();
        validateIfVideoIsMusic(videoSnippet);
        YoutubeMusic youtubeMusic = YoutubeMusic.builder()
                .youtubeId(youtubeId)
                .title(videoSnippet.getTitle())
                .thumbnailUrl(videoSnippet.getThumbnails().getStandard().getUrl())
                .build();
        youtubeMusicRepository.save(youtubeMusic);
        return youtubeMusic;
    }

    private Video getYoutubeVideo(String youtubeId) {
        return youtubeApi.getVideo(youtubeId);
    }

    private void validateIfVideoIsMusic(VideoSnippet snippet) {
        if (!snippet.getDescription().startsWith("Provided to YouTube")) {
            throw new BadRequestException(ErrorCode.INVALID_YOUTUBE_MUSIC_ID);
        }
    }
}
