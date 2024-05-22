package com.musicinabottle.music;

import com.musicinabottle.music.streaming.youtube.InvalidYoutubeMusicIdException;
import com.musicinabottle.music.streaming.youtube.InvalidYoutubeMusicUrlException;
import com.musicinabottle.music.streaming.youtube.YoutubeMusic;
import com.musicinabottle.music.streaming.youtube.YoutubeMusicNotFoundException;
import com.musicinabottle.music.streaming.youtube.YoutubeMusicService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MusicService {
    private final YoutubeMusicService youtubeMusicService;

    public YoutubeMusic getYoutubeMusicByUrl(String youtubeMusicUrl) throws InvalidYoutubeMusicUrlException, YoutubeMusicNotFoundException, InvalidYoutubeMusicIdException,
            IOException {
        return youtubeMusicService.getYoutubeMusic(youtubeMusicUrl);
    }
}
