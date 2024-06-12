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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalloonService {

    public final static int BALLOON_PAGE_SIZE = 100;
    private final BalloonRepository balloonRepository;

    public Balloon createYoutubeMusicBalloon(YoutubeMusic youtubeMusic, BigDecimal latitude, BigDecimal longitude, User creator) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(creator)
                .baseLat(latitude)
                .baseLon(longitude)
                .build();
        return balloonRepository.save(balloon);
    }

    public Balloon createSpotifyMusicBalloon(SpotifyMusic spotifyMusic, BigDecimal latitude, BigDecimal longitude, User creator) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.SPOTIFY_MUSIC)
                .spotifyMusic(spotifyMusic)
                .creator(creator)
                .baseLat(latitude)
                .baseLon(longitude)
                .build();
        return balloonRepository.save(balloon);
    }

    @Deprecated
    public Balloon pickRandomBalloon() {
        List<Balloon> balloons = balloonRepository.findAll();
        return balloons.getFirst();
    }

    public Balloon getBalloon(Long balloonId) {
        return balloonRepository.findById(balloonId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BALLOON_NOT_FOUND));
    }

    public List<Balloon> getBalloonList(int page) {
        return balloonRepository.findAll(PageRequest.of(page, BALLOON_PAGE_SIZE)).getContent();
    }
}
