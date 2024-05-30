package com.musicinabottle.bottle;

import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.MusicService;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.user.User;
import com.musicinabottle.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BottleFacade {

    private final BottleService bottleService;
    private final MusicService musicService;
    private final UserService userService;

    public BottleResponse pickRandomBottle() {
        return BottleResponse.of(bottleService.pickRandomBottle());
    }

    public BottleResponse createBottle(@NonNull String streamingMusicUrl, @NonNull Long ownerId) {
        User user = userService.getUser(ownerId);
        StreamingMusicType type = musicService.checkStreamingMusicType(streamingMusicUrl);
        Bottle bottle = switch (type) {
            case YOUTUBE_MUSIC -> createYoutubeMusicBottle(streamingMusicUrl, user);
            case SPOTIFY_MUSIC -> createSpotifyMusicBottle(streamingMusicUrl, user);
        };
        return BottleResponse.of(bottle);
    }

    private Bottle createYoutubeMusicBottle(String youtubeMusicUrl, User owner) {
        var youtubeMusic = musicService.getYoutubeMusicByUrl(youtubeMusicUrl);
        return bottleService.createYoutubeMusicBottle(youtubeMusic, owner);
    }

    private Bottle createSpotifyMusicBottle(String spotifyMusicUrl, User owner) {
        var spotifyMusic = musicService.getSpotifyMusicByUrl(spotifyMusicUrl);
        return bottleService.createSpotifyMusicBottle(spotifyMusic, owner);
    }
}
