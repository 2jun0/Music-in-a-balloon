package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.music.application.MusicService;
import com.musicinaballoon.music.domain.StreamingMusic;
import com.musicinaballoon.notification.application.ReactionNotificationFacade;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.application.WaveService;
import com.musicinaballoon.wave.domain.Wave;
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
    private final ReactionNotificationFacade reactionNotificationFacade;

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

            notifyReaction(balloonReaction.getBalloon().getCreator().getId(), balloonReaction);
        } else {
            balloonPickService.validatePicked(balloonId, userId);
            Balloon balloon = balloonService.getBalloon(balloonId);

            User user = userService.getUser(userId);
            BalloonReaction balloonReaction = balloonReactionService.createBalloonReaction(balloon, user,
                    request.balloonReactionType());

            notifyReaction(balloon.getCreator().getId(), balloonReaction);
        }
    }

    private void notifyReaction(Long receiverId, BalloonReaction balloonReaction) {
        reactionNotificationFacade.sendNotification(receiverId, balloonReaction);
    }

    public void deleteBalloonReaction(Long balloonId, Long userId) {
        balloonReactionService.validateBalloonReactionExisted(balloonId, userId);
        balloonReactionService.deleteBalloonReaction(balloonId, userId);
    }

    public BalloonResponse createBalloon(CreateBalloonRequest request, Long ownerId) {
        User user = userService.getUser(ownerId);
        StreamingMusic streamingMusic = getStreamingMusic(request.streamingMusicUrl());

        Balloon balloon = balloonService.createBalloon(streamingMusic, request.latitude(), request.longitude(), user,
                request.message(), request.colorCode());
        return BalloonResponse.from(balloon);
    }

    private StreamingMusic getStreamingMusic(String streamingUrl) {
        return switch (musicService.checkStreamingMusicType(streamingUrl)) {
            case YOUTUBE_MUSIC -> musicService.getYoutubeMusicByUrl(streamingUrl);
            case SPOTIFY_MUSIC -> musicService.getSpotifyMusicByUrl(streamingUrl);
        };
    }

    public BalloonListResponse getBalloonList(Long userId, int page) {
        List<Balloon> balloons = balloonService.getNotPickedBalloonList(userId, page);
        return BalloonListResponse.from(balloons);
    }

    public BalloonListResponse getPickedBalloonList(Long pickerId, int page) {
        List<Balloon> balloonList = balloonService.getPickedBalloonList(pickerId, page);
        return BalloonListResponse.from(balloonList);
    }
}
