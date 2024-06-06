package com.musicinaballoon.balloon;

import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.NotFoundException;
import com.musicinaballoon.music.streaming.StreamingMusicType;
import com.musicinaballoon.music.streaming.spotify.SpotifyMusic;
import com.musicinaballoon.music.streaming.youtube.YoutubeMusic;
import com.musicinaballoon.user.User;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalloonService {
    private final BalloonRepository balloonRepository;

    public Balloon createYoutubeMusicBalloon(YoutubeMusic youtubeMusic, BigDecimal latitude, BigDecimal longitude, User creator) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(creator)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return balloonRepository.save(balloon);
    }

    public Balloon createSpotifyMusicBalloon(SpotifyMusic spotifyMusic, BigDecimal latitude, BigDecimal longitude, User creator) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.SPOTIFY_MUSIC)
                .spotifyMusic(spotifyMusic)
                .creator(creator)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return balloonRepository.save(balloon);
    }

    public Balloon pickRandomBalloon() {
        List<Balloon> balloons = balloonRepository.findAll();
        return balloons.getFirst();
    }

    public Balloon getBalloon(Long balloonId) {
        return balloonRepository.findById(balloonId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BALLOON_NOT_FOUND));
    }
}
