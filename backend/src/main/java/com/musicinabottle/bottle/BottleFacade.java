package com.musicinabottle.bottle;

import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.MusicService;
import com.musicinabottle.music.streaming.StreamingMusicType;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

@RequiredArgsConstructor
@Component
public class BottleFacade {

    private final BottleService bottleService;
    private final MusicService musicService;

    public BottleResponse pickRandomBottle() {
        return BottleResponse.of(bottleService.pickRandomBottle());
    }

    public BottleResponse createBottle(String streamingMusicUrl) throws IOException, ParseException, SpotifyWebApiException {
        StreamingMusicType type = musicService.checkStreamingMusicType(streamingMusicUrl);
        Bottle bottle = switch (type) {
            case YOUTUBE_MUSIC -> createYoutubeMusicBottle(streamingMusicUrl);
            case SPOTIFY_MUSIC -> createSpotifyMusicBottle(streamingMusicUrl);
        };
        return BottleResponse.of(bottle);
    }

    private Bottle createYoutubeMusicBottle(String youtubeMusicUrl) throws IOException {
        return bottleService.createYoutubeMusicBottle(musicService.getYoutubeMusicByUrl(youtubeMusicUrl));
    }

    private Bottle createSpotifyMusicBottle(String spotifyMusicUrl) throws IOException, ParseException, SpotifyWebApiException {
        return bottleService.createSpotifyMusicBottle(musicService.getSpotifyMusicByUrl(spotifyMusicUrl));
    }
}
