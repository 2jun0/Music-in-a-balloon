package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.common.util.GeolocationUtil.distanceBetween;
import static com.musicinaballoon.common.util.TimeUtil.secondTimeDifference;
import static com.musicinaballoon.common.util.TimeUtil.utcNow;

import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.music.application.MusicService;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.application.WaveService;
import com.musicinaballoon.wave.domain.Geolocation;
import com.musicinaballoon.wave.domain.Wave;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@RequiredArgsConstructor
@Transactional
@Component
public class BalloonFacade {

    private final BalloonService balloonService;
    private final BalloonPickService balloonPickService;
    private final MusicService musicService;
    private final UserService userService;
    private final WaveService waveService;

    private final double balloonPickReachKilometerLimit;

    public BalloonFacade(BalloonService balloonService, BalloonPickService balloonPickService, MusicService musicService,
            UserService userService, WaveService waveService,
            @Value("${balloon.pick-reach-kilometer-limit}") double balloonPickReachKilometerLimit) {
        this.balloonService = balloonService;
        this.balloonPickService = balloonPickService;
        this.musicService = musicService;
        this.userService = userService;
        this.waveService = waveService;
        this.balloonPickReachKilometerLimit = balloonPickReachKilometerLimit;
    }

    private static Geolocation getCurrentBalloonGeolocation(Balloon balloon, Wave wave) {
        long time = secondTimeDifference(utcNow(), balloon.getBasedAt());
        Geolocation currentBalloonGeolocation = wave.calculateGeolocation(balloon.getBaseLatitude(), balloon.getBaseLongitude(),
                time);
        return currentBalloonGeolocation;
    }

    public BalloonResponse getBalloon(Long balloonId) {
        Balloon balloon = balloonService.getBalloon(balloonId);
        return BalloonResponse.from(balloon);
    }

    public BalloonResponse pickBalloon(Long balloonId, Long userId, PickBalloonRequest request) {
        balloonPickService.validateNotPicked(balloonId, userId);

        Balloon balloon = balloonService.getBalloon(balloonId);
        User user = userService.getUser(userId);
        Wave wave = waveService.getCurrentWave();
        validateBalloonInReach(request.userLatitude(), request.userLongitude(), balloon, wave);

        balloonPickService.pickBalloon(balloon, user);
        return BalloonResponse.from(balloon);
    }

    private void validateBalloonInReach(BigDecimal userLatitude, BigDecimal userLongitude, Balloon balloon, Wave wave) {
        Geolocation currentBalloonGeolocation = getCurrentBalloonGeolocation(balloon, wave);

        double distance = distanceBetween(currentBalloonGeolocation.latitude(), userLatitude,
                currentBalloonGeolocation.longitude(),
                userLongitude);

        if (distance > balloonPickReachKilometerLimit) {
            throw new BadRequestException(ErrorCode.TOO_FAR_TO_PICK_BALLOON);
        }
    }

    public void reactBalloon(Long balloonId, ReactBalloonRequest request, Long userId) {
        balloonPickService.validatePicked(balloonId, userId);

        BalloonPicked balloonPicked = balloonPickService.getBalloonPicked(balloonId, userId);
        balloonPicked.setReactType(request.balloonReactType());
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

    public BalloonListResponse getBalloonList(Long userId, int page) {
        User user = userService.getUser(userId);
        List<Balloon> balloons = balloonService.getNotPickedBalloonList(user, page);
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
