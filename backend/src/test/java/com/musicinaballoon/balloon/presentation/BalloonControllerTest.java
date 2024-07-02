package com.musicinaballoon.balloon.presentation;

import static com.musicinaballoon.fixture.BalloonFixture.DEFAULT_MESSAGE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LONGITUDE;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.repository.BalloonRepository;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;

abstract class BalloonControllerTest extends IntegrationTest {

    @Autowired
    protected YoutubeMusicRepository youtubeMusicRepository;

    @Autowired
    protected BalloonRepository balloonRepository;

    protected Balloon createDefaultBalloon(YoutubeMusic youtubeMusic) {
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(defaultUser)
                .baseLatitude(PYRAMID_OF_KHUFU_LATITUDE)
                .baseLongitude(PYRAMID_OF_KHUFU_LONGITUDE)
                .message(DEFAULT_MESSAGE)
                .build();
        balloonRepository.save(balloon);
        return balloon;
    }

    protected YoutubeMusic createDefaultYoutubeMusic() {
        YoutubeMusic youtubeMusic = new YoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_URL, YOUTUBE_MUSIC_SUPER_SHY_TITLE, null);
        youtubeMusicRepository.save(youtubeMusic);
        return youtubeMusic;
    }
}