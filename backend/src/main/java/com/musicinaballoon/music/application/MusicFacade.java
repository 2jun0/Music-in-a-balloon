package com.musicinaballoon.music.application;

import com.musicinaballoon.music.application.request.GetMusicParam;
import com.musicinaballoon.music.application.response.MusicResponse;
import com.musicinaballoon.music.application.response.SpotifyMusicResponse;
import com.musicinaballoon.music.application.response.YoutubeMusicResponse;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusic;
import com.musicinaballoon.music.domain.YoutubeMusic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicFacade {

    private final MusicService musicService;

    public MusicResponse getMusic(GetMusicParam param) {
        StreamingMusic streamingMusic = getStreamingMusic(param.streamingUrl());

        return switch (streamingMusic.getStreamingMusicType()) {
            case YOUTUBE_MUSIC -> YoutubeMusicResponse.from((YoutubeMusic) streamingMusic);
            case SPOTIFY_MUSIC -> SpotifyMusicResponse.from((SpotifyMusic) streamingMusic);
        };
    }

    private StreamingMusic getStreamingMusic(String streamingUrl) {
        return switch (musicService.checkStreamingMusicType(streamingUrl)) {
            case YOUTUBE_MUSIC -> musicService.getYoutubeMusicByUrl(streamingUrl);
            case SPOTIFY_MUSIC -> musicService.getSpotifyMusicByUrl(streamingUrl);
        };
    }
}
