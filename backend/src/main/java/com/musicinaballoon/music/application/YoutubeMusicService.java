package com.musicinaballoon.music.application;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.external.YoutubeApi;
import com.musicinaballoon.music.external.reponse.YoutubeVideo;
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
        YoutubeVideo video = youtubeApi.getVideo(youtubeId);
        validateIfVideoIsMusic(video);
        YoutubeMusic youtubeMusic = YoutubeMusic.builder()
                .youtubeId(video.id())
                .title(video.title())
                .thumbnailUrl(video.thumbnail().standardUrl())
                .build();
        return youtubeMusicRepository.save(youtubeMusic);
    }

    private void validateIfVideoIsMusic(YoutubeVideo video) {
        if (!video.description().startsWith("Provided to YouTube")) {
            throw new BadRequestException(ErrorCode.INVALID_YOUTUBE_MUSIC_ID);
        }
    }
}
