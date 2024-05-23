package com.musicinabottle.bottle;

import com.musicinabottle.music.MusicService;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.youtube.InvalidYoutubeMusicIdException;
import com.musicinabottle.music.streaming.youtube.InvalidYoutubeMusicUrlException;
import com.musicinabottle.music.streaming.youtube.YoutubeMusicNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BottleService {

    private final BottleRepository bottleRepository;
    private final MusicService musicService;

    public Bottle createNewYoutubeMusicBottle(String youtubeMusicUrl) throws InvalidYoutubeMusicUrlException, YoutubeMusicNotFoundException, InvalidYoutubeMusicIdException, IOException {
        return Bottle.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(musicService.getYoutubeMusicByUrl(youtubeMusicUrl))
                .build();
    }

    public Bottle pickRandomBottle() {
        return bottleRepository.findById(1L).orElseThrow();
    }
}
