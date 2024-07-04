package com.musicinaballoon.balloon.presentation;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.BalloonPickedFixture.balloonPickedBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.balloon.repository.BalloonPickedRepository;
import com.musicinaballoon.balloon.repository.BalloonRepository;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;

abstract class BalloonControllerTest extends IntegrationTest {

    @Autowired
    protected YoutubeMusicRepository youtubeMusicRepository;

    @Autowired
    protected BalloonRepository balloonRepository;

    @Autowired
    protected BalloonPickedRepository balloonPickedRepository;

    protected Balloon createDefaultBalloon(YoutubeMusic youtubeMusic) {
        return balloonRepository.save(youtubeMusicBalloonBuilder(youtubeMusic, defaultUser).build());
    }

    protected YoutubeMusic createDefaultYoutubeMusic() {
        return youtubeMusicRepository.save(youtubeMusicBuilder().build());
    }

    protected BalloonPicked createBalloonPicked(Balloon balloon) {
        return balloonPickedRepository.save(balloonPickedBuilder(balloon, defaultUser).build());
    }
}