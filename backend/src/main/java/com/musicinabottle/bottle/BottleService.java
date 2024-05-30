package com.musicinabottle.bottle;

import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.spotify.SpotifyMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
import com.musicinabottle.user.User;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BottleService {
    private final BottleRepository bottleRepository;

    public Bottle createYoutubeMusicBottle(@NonNull YoutubeMusic youtubeMusic, @NonNull User creator) {
        Bottle bottle = Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(creator)
                .build();
        bottleRepository.save(bottle);
        return bottle;
    }

    public Bottle createSpotifyMusicBottle(@NonNull SpotifyMusic spotifyMusic, @NonNull User creator) {
        Bottle bottle = Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.SPOTIFY_MUSIC)
                .spotifyMusic(spotifyMusic)
                .creator(creator)
                .build();
        bottleRepository.save(bottle);
        return bottle;
    }

    public Bottle pickRandomBottle() {
        List<Bottle> bottles = bottleRepository.findAll();
        return bottles.getFirst();
    }
}
