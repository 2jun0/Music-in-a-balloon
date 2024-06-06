package com.musicinaballoon.music.application;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.StreamingMusicUrlParser;
import com.musicinaballoon.music.domain.YoutubeMusic;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MusicService {
    private final Map<StreamingMusicType, StreamingMusicUrlParser> urlParsers;
    private final YoutubeMusicService youtubeMusicService;
    private final SpotifyMusicService spotifyMusicService;

    public MusicService(List<StreamingMusicUrlParser> urlParsers, YoutubeMusicService youtubeMusicService,
            SpotifyMusicService spotifyMusicService) {
        this.urlParsers = urlParsers.stream()
                .collect(Collectors.toMap(StreamingMusicUrlParser::type, Function.identity()));
        this.youtubeMusicService = youtubeMusicService;
        this.spotifyMusicService = spotifyMusicService;
    }

    public StreamingMusicType checkStreamingMusicType(String streamingMusicUrl) {
        for (var entry : urlParsers.entrySet()) {
            if (entry.getValue().check(streamingMusicUrl)) {
                return entry.getKey();
            }
        }
        throw new BadRequestException(ErrorCode.INVALID_STREAMING_MUSIC_URL);
    }

    public YoutubeMusic getYoutubeMusicByUrl(String youtubeMusicUrl) {
        String youtubeId = urlParsers.get(StreamingMusicType.YOUTUBE_MUSIC).extractId(youtubeMusicUrl);
        return youtubeMusicService.getYoutubeMusic(youtubeId);
    }

    public SpotifyMusic getSpotifyMusicByUrl(String spotifyMusicUrl) {
        String spotifyId = urlParsers.get(StreamingMusicType.SPOTIFY_MUSIC).extractId(spotifyMusicUrl);
        return spotifyMusicService.getSpotifyMusic(spotifyId);
    }
}
