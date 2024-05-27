package com.musicinabottle.bottle;

import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.MusicService;
import com.musicinabottle.music.streaming.StreamingMusicType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BottleFacade {

    private final BottleService bottleService;
    private final MusicService musicService;

    public BottleResponse pickRandomBottle() {
        return BottleResponse.of(bottleService.pickRandomBottle());
    }

    public BottleResponse createBottle(String streamingMusicUrl) {
        StreamingMusicType type = musicService.checkStreamingMusicType(streamingMusicUrl);
        Bottle bottle = switch (type) {
            case YOUTUBE_MUSIC -> createYoutubeMusicBottle(streamingMusicUrl);
            case SPOTIFY_MUSIC -> createSpotifyMusicBottle(streamingMusicUrl);
        };
        return BottleResponse.of(bottle);
    }

    private Bottle createYoutubeMusicBottle(String youtubeMusicUrl) {
        return bottleService.createYoutubeMusicBottle(musicService.getYoutubeMusicByUrl(youtubeMusicUrl));
    }

    private Bottle createSpotifyMusicBottle(String spotifyMusicUrl) {
        return bottleService.createSpotifyMusicBottle(musicService.getSpotifyMusicByUrl(spotifyMusicUrl));
    }
}
