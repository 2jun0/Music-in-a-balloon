package com.musicinabottle.bottle;

import com.musicinabottle.music.MusicService;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.spotify.SpotifyMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BottleService {
    private final BottleRepository bottleRepository;
    private final MusicService musicService;

    public Bottle createYoutubeMusicBottle(YoutubeMusic youtubeMusic) {
        return Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .build();
    }

    public Bottle createSpotifyMusicBottle(SpotifyMusic spotifyMusic) {
        return Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.SPOTIFY_MUSIC)
                .spotifyMusic(spotifyMusic)
                .build();
    }

    public Bottle pickRandomBottle() {
        return bottleRepository.findById(1L).orElseThrow();
    }
}
