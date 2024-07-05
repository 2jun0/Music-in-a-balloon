package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.music.application.MusicService;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.application.WaveService;
import com.musicinaballoon.wave.domain.Wave;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@RequiredArgsConstructor
@Transactional
@Component
@RequiredArgsConstructor
public class BalloonFacade {

    private final BalloonService balloonService;
    private final BalloonPickService balloonPickService;
    private final BalloonReactionService balloonReactionService;
    private final MusicService musicService;
    private final UserService userService;
    private final WaveService waveService;

    public BalloonResponse getBalloon(Long balloonId) {
        Balloon balloon = balloonService.getBalloon(balloonId);
        return BalloonResponse.from(balloon);
    }

    public BalloonResponse pickBalloon(Long balloonId, Long userId, PickBalloonRequest request) {
        balloonPickService.validateNotPicked(balloonId, userId);

        Balloon balloon = balloonService.getBalloon(balloonId);
        User user = userService.getUser(userId);
        Wave wave = waveService.getCurrentWave();
        balloonPickService.validateBalloonInReach(request.userLatitude(), request.userLongitude(), balloon, wave);

        balloonPickService.pickBalloon(balloon, user);
        return BalloonResponse.from(balloon);
    }

    public void reactBalloon(Long balloonId, ReactBalloonRequest request, Long userId) {
        if (balloonReactionService.existsBalloonReaction(balloonId, userId)) {
            BalloonReaction balloonReaction = balloonReactionService.getBalloonReaction(balloonId, userId);
            balloonReaction.setType(request.balloonReactionType());
        } else {
            balloonPickService.validatePicked(balloonId, userId);
            Balloon balloon = balloonService.getBalloon(balloonId);
            User user = userService.getUser(userId);

            balloonReactionService.createBalloonReaction(balloon, user, request.balloonReactionType());
        }
    }

    public void deleteBalloonReaction(Long balloonId, Long userId) {
        balloonReactionService.validateBalloonReactionExisted(balloonId, userId);
        balloonReactionService.deleteBalloonReaction(balloonId, userId);
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
