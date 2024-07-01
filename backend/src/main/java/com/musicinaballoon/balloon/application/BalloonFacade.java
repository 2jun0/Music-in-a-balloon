package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.common.util.GeolocationUtil.distanceBetween;
import static com.musicinaballoon.common.util.TimeUtil.mircoTimeDifference;
import static com.musicinaballoon.common.util.TimeUtil.utcNow;

import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
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
    private final BalloonReplyService balloonReplyService;
    private final MusicService musicService;
    private final UserService userService;
    private final WaveService waveService;

    private final double balloonPickReachKilometerLimit;

    public BalloonFacade(BalloonService balloonService, BalloonReplyService balloonReplyService, MusicService musicService,
            UserService userService, WaveService waveService,
            @Value("${balloon.pick-reach-kilometer-limit}") double balloonPickReachKilometerLimit) {
        this.balloonService = balloonService;
        this.balloonReplyService = balloonReplyService;
        this.musicService = musicService;
        this.userService = userService;
        this.waveService = waveService;
        this.balloonPickReachKilometerLimit = balloonPickReachKilometerLimit;
    }

    private static Geolocation getCurrentBalloonGeolocation(Balloon balloon, Wave wave) {
        long time = mircoTimeDifference(utcNow(), balloon.getBasedAt());
        Geolocation currentBalloonGeolocation = wave.calculateGeolocation(balloon.getBaseLat(), balloon.getBaseLon(), time);
        return currentBalloonGeolocation;
    }

    public BalloonResponse getBalloon(Long balloonId) {
        Balloon balloon = balloonService.getBalloon(balloonId);
        return BalloonResponse.from(balloon);
    }

    public BalloonResponse pickBalloon(Long balloonId, Long userId, PickBalloonRequest request) {
        Balloon balloon = balloonService.getBalloon(balloonId);
        User user = userService.getUser(userId);
        Wave wave = waveService.getCurrentWave();
        validateBalloonInReach(request.latitude(), request.longitude(), balloon, wave);

        balloonReplyService.createBalloonReply(balloon, user, request.reply());
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
