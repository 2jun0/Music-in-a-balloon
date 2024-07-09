package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.music.domain.StreamingMusicType.SPOTIFY_MUSIC;
import static com.musicinaballoon.music.domain.StreamingMusicType.YOUTUBE_MUSIC;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.Balloon.BalloonBuilder;
import com.musicinaballoon.balloon.repository.BalloonRepository;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.NotFoundException;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusic;
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

    public BalloonService(BalloonRepository balloonRepository,
            @Value("${balloon.list-page-size}") int balloonListPageSize) {
        this.balloonRepository = balloonRepository;
        this.balloonListPageSize = balloonListPageSize;
    }

    public Balloon createBalloon(StreamingMusic streamingMusic, BigDecimal latitude, BigDecimal longitude, User creator
            , String message, String colorCode) {
        BalloonBuilder balloonBuilder = Balloon.builder()
                .creator(creator)
                .baseLatitude(latitude)
                .baseLongitude(longitude)
                .message(message)
                .colorCode(colorCode);

        return balloonRepository.save(
                switch (streamingMusic.getStreamingMusicType()) {
                    case YOUTUBE_MUSIC -> balloonBuilder.uploadedStreamingMusicType(YOUTUBE_MUSIC)
                            .youtubeMusic((YoutubeMusic) streamingMusic)
                            .build();
                    case SPOTIFY_MUSIC -> balloonBuilder.uploadedStreamingMusicType(SPOTIFY_MUSIC)
                            .spotifyMusic((SpotifyMusic) streamingMusic)
                            .build();
                });
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
