package com.musicinabottle.bottle;

import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.spotify.SpotifyMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
import com.musicinabottle.user.User;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BottleService {
    private final BottleRepository bottleRepository;

    public Bottle createYoutubeMusicBottle(YoutubeMusic youtubeMusic, BigDecimal latitude, BigDecimal longitude, User creator) {
        Bottle bottle = Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(creator)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        bottleRepository.save(bottle);
        return bottle;
    }

    public Bottle createSpotifyMusicBottle(SpotifyMusic spotifyMusic, BigDecimal latitude, BigDecimal longitude, User creator) {
        Bottle bottle = Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.SPOTIFY_MUSIC)
                .spotifyMusic(spotifyMusic)
                .creator(creator)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        bottleRepository.save(bottle);
        return bottle;
    }

    public Bottle pickRandomBottle() {
        List<Bottle> bottles = bottleRepository.findAll();
        return bottles.getFirst();
    }
}
