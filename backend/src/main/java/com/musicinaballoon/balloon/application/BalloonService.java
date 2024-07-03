package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.repository.BalloonRepository;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.NotFoundException;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.user.domain.User;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BalloonService {

    private final BalloonRepository balloonRepository;
    private final int balloonListPageSize;

    public BalloonService(BalloonRepository balloonRepository, @Value("${balloon.list-page-size}") int balloonListPageSize) {
        this.balloonRepository = balloonRepository;
        this.balloonListPageSize = balloonListPageSize;
    }

    public Balloon createYoutubeMusicBalloon(YoutubeMusic youtubeMusic, BigDecimal latitude, BigDecimal longitude, User creator
            , String message) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(creator)
                .baseLatitude(latitude)
                .baseLongitude(longitude)
                .message(message)
                .build();
        return balloonRepository.save(balloon);
    }

    public Balloon createSpotifyMusicBalloon(SpotifyMusic spotifyMusic, BigDecimal latitude, BigDecimal longitude, User creator
            , String message) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.SPOTIFY_MUSIC)
                .spotifyMusic(spotifyMusic)
                .creator(creator)
                .baseLatitude(latitude)
                .baseLongitude(longitude)
                .message(message)
                .build();
        return balloonRepository.save(balloon);
    }

    public Balloon getBalloon(Long balloonId) {
        return findBalloonById(balloonId);
    }

    private Balloon findBalloonById(Long balloonId) {
        return balloonRepository.findById(balloonId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BALLOON_NOT_FOUND));
    }

    public List<Balloon> getNotPickedBalloonList(User user, int page) {
        return balloonRepository.findNotPickedNotCreatedByUserOrderByCreatedAtDesc(user, PageRequest.of(page,
                balloonListPageSize));
    }
}
