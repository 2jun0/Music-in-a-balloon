package com.musicinabottle.music;

import com.musicinabottle.music.streaming.spotify.SpotifyMusic;
import com.musicinabottle.music.streaming.spotify.SpotifyMusicService;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusicService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

@RequiredArgsConstructor
@Service
public class MusicService {
    private final YoutubeMusicService youtubeMusicService;
    private final SpotifyMusicService spotifyMusicService;

    public YoutubeMusic getYoutubeMusicByUrl(String youtubeMusicUrl) throws IOException {
        return youtubeMusicService.getYoutubeMusic(youtubeMusicUrl);
    }

    public SpotifyMusic getSpotifyMusicByUrl(String spotifyMusicUrl) throws IOException, ParseException, SpotifyWebApiException {
        return spotifyMusicService.getSpotifyMusic(spotifyMusicUrl);
    }
}
