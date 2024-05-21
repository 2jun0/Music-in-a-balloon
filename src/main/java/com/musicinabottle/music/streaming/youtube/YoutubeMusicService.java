package com.musicinabottle.music.streaming.youtube;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeMusicService {
    private final YoutubeMusicRepository youtubeMusicRepository;
    private final YoutubeMusicIdExtractor musicIdExtractor;
    private final YoutubeApi youtubeApi;

    public YoutubeMusic getYoutubeMusic(String youtubeMusicUrl) throws YoutubeMusicNotFoundException, IOException, InvalidYoutubeMusicIdException, InvalidYoutubeMusicUrlException {
        String youtubeId = musicIdExtractor.extractId(youtubeMusicUrl);
        return youtubeMusicRepository.findByYoutubeId(youtubeId)
                .orElse(createNewYoutubeMusic(youtubeMusicUrl));
    }

    private YoutubeMusic createNewYoutubeMusic(String youtubeId) throws YoutubeMusicNotFoundException, IOException, InvalidYoutubeMusicIdException {
        Video video = getYoutubeVideo(youtubeId);
        VideoSnippet videoSnippet = video.getSnippet();
        validateIfVideoIsMusic(videoSnippet);

        YoutubeMusic youtubeMusic = YoutubeMusic.builder()
                .youtubeId(youtubeId)
                .title(videoSnippet.getTitle())
                .thumbnailUrl(videoSnippet.getThumbnails().getDefault().getUrl())
                .build();

        youtubeMusicRepository.save(youtubeMusic);
        return youtubeMusic;
    }

    private Video getYoutubeVideo(String youtubeId) throws IOException, YoutubeMusicNotFoundException {
        return youtubeApi.getVideo(youtubeId)
                .orElseThrow(YoutubeMusicNotFoundException::new);
    }

    private void validateIfVideoIsMusic(VideoSnippet snippet) throws InvalidYoutubeMusicIdException {
        if (!snippet.getDescription().startsWith("Provided to YouTube")) {
            throw new InvalidYoutubeMusicIdException();
        }
    }
}
