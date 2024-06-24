package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.application.MusicService;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import java.math.BigDecimal;
import java.util.List;
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
        return BalloonResponse.from(balloon);
    }

    @Deprecated
    public BalloonResponse pickRandomBalloon() {
        return BalloonResponse.from(balloonService.pickRandomBalloon());
    }

    public BalloonResponse createBalloon(CreateBalloonRequest request, Long ownerId) {
        User user = userService.getUser(ownerId);
        StreamingMusicType type = musicService.checkStreamingMusicType(request.streamingMusicUrl());
        Balloon balloon = switch (type) {
            case YOUTUBE_MUSIC ->
                    createYoutubeMusicBalloon(request.streamingMusicUrl(), request.latitude(), request.longitude(), user,
                            request.message());
            case SPOTIFY_MUSIC ->
                    createSpotifyMusicBalloon(request.streamingMusicUrl(), request.latitude(), request.longitude(), user,
                            request.message());
        };
        return BalloonResponse.from(balloon);
    }

    public BalloonListResponse getBalloonList(int page) {
        List<Balloon> balloons = balloonService.getBalloonListSortedByCreatedAt(page);
        return BalloonListResponse.from(balloons);
    }

    private Balloon createYoutubeMusicBalloon(String youtubeMusicUrl, BigDecimal latitude, BigDecimal longitude, User owner,
            String message) {
        var youtubeMusic = musicService.getYoutubeMusicByUrl(youtubeMusicUrl);
        return balloonService.createYoutubeMusicBalloon(youtubeMusic, latitude, longitude, owner, message);
    }

    private Balloon createSpotifyMusicBalloon(String spotifyMusicUrl, BigDecimal latitude, BigDecimal longitude, User owner,
            String message) {
        var spotifyMusic = musicService.getSpotifyMusicByUrl(spotifyMusicUrl);
        return balloonService.createSpotifyMusicBalloon(spotifyMusic, latitude, longitude, owner, message);
    }
}
