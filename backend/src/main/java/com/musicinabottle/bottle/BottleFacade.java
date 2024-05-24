package com.musicinabottle.bottle;

import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.MusicService;
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

    public BottleResponse createYoutubeMusicBottle(String youtubeMusicUrl) throws IOException {
        Bottle bottle = bottleService.createYoutubeMusicBottle(musicService.getYoutubeMusicByUrl(youtubeMusicUrl));
        return BottleResponse.of(bottle);
    }

    public BottleResponse createSpotifyMusicBottle(String spotifyMusicUrl) throws IOException, ParseException, SpotifyWebApiException {
        Bottle bottle = bottleService.createSpotifyMusicBottle(musicService.getSpotifyMusicByUrl(spotifyMusicUrl));
        return BottleResponse.of(bottle);
    }
}
