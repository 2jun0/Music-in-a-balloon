package com.musicinaballoon.balloon;

import com.musicinaballoon.balloon.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.response.BalloonResponse;
import com.musicinaballoon.music.application.MusicService;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.user.User;
import com.musicinaballoon.user.UserService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class BalloonFacade {

    private final BalloonService balloonService;
    private final MusicService musicService;
    private final UserService userService;

    public BalloonResponse getBalloon(Long balloonId) {
        Balloon balloon = balloonService.getBalloon(balloonId);
        return BalloonResponse.of(balloon);
    }

    @Deprecated
    public BalloonResponse pickRandomBalloon() {
        return BalloonResponse.of(balloonService.pickRandomBalloon());
    }

    public BalloonResponse createBalloon(CreateBalloonRequest request, Long ownerId) {
        User user = userService.getUser(ownerId);
        StreamingMusicType type = musicService.checkStreamingMusicType(request.streamingMusicUrl());
        Balloon balloon = switch (type) {
            case YOUTUBE_MUSIC ->
                    createYoutubeMusicBalloon(request.streamingMusicUrl(), request.latitude(), request.longitude(), user);
            case SPOTIFY_MUSIC ->
                    createSpotifyMusicBalloon(request.streamingMusicUrl(), request.latitude(), request.longitude(), user);
        };
        return BalloonResponse.of(balloon);
    }

    private Balloon createYoutubeMusicBalloon(String youtubeMusicUrl, BigDecimal latitude, BigDecimal longitude, User owner) {
        var youtubeMusic = musicService.getYoutubeMusicByUrl(youtubeMusicUrl);
        return balloonService.createYoutubeMusicBalloon(youtubeMusic, latitude, longitude, owner);
    }

    private Balloon createSpotifyMusicBalloon(String spotifyMusicUrl, BigDecimal latitude, BigDecimal longitude, User owner) {
        var spotifyMusic = musicService.getSpotifyMusicByUrl(spotifyMusicUrl);
        return balloonService.createSpotifyMusicBalloon(spotifyMusic, latitude, longitude, owner);
    }
}
