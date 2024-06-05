package com.musicinabottle.bottle;

import com.musicinabottle.bottle.request.CreateBottleRequest;
import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.MusicService;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.user.User;
import com.musicinabottle.user.UserService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class BottleFacade {

    private final BottleService bottleService;
    private final MusicService musicService;
    private final UserService userService;

    public BottleResponse pickRandomBottle() {
        return BottleResponse.of(bottleService.pickRandomBottle());
    }

    public BottleResponse createBottle(CreateBottleRequest request, Long ownerId) {
        User user = userService.getUser(ownerId);
        StreamingMusicType type = musicService.checkStreamingMusicType(request.streamingMusicUrl());
        Bottle bottle = switch (type) {
            case YOUTUBE_MUSIC -> createYoutubeMusicBottle(request.streamingMusicUrl(), request.latitude(), request.longitude(), user);
            case SPOTIFY_MUSIC -> createSpotifyMusicBottle(request.streamingMusicUrl(), request.latitude(), request.longitude(), user);
        };
        return BottleResponse.of(bottle);
    }

    private Bottle createYoutubeMusicBottle(String youtubeMusicUrl, BigDecimal latitude, BigDecimal longitude, User owner) {
        var youtubeMusic = musicService.getYoutubeMusicByUrl(youtubeMusicUrl);
        return bottleService.createYoutubeMusicBottle(youtubeMusic, latitude, longitude, owner);
    }

    private Bottle createSpotifyMusicBottle(String spotifyMusicUrl, BigDecimal latitude, BigDecimal longitude, User owner) {
        var spotifyMusic = musicService.getSpotifyMusicByUrl(spotifyMusicUrl);
        return bottleService.createSpotifyMusicBottle(spotifyMusic, latitude, longitude, owner);
    }
}
