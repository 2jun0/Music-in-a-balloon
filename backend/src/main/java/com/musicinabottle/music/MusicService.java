package com.musicinabottle.music;

import com.musicinabottle.music.streaming.InvalidStreamingMusicException;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.StreamingMusicUrlParser;
import com.musicinabottle.music.streaming.spotify.SpotifyMusic;
import com.musicinabottle.music.streaming.spotify.SpotifyMusicService;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusicService;
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

    public MusicService(List<StreamingMusicUrlParser> urlParsers, YoutubeMusicService youtubeMusicService, SpotifyMusicService spotifyMusicService) {
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
        throw new InvalidStreamingMusicException();
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
