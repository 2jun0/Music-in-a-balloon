package com.musicinaballoon.fixture;

import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LONGITUDE;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.Balloon.BalloonBuilder;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.user.domain.User;
import java.math.BigDecimal;

public final class BalloonFixture {

    public static final String DEFAULT_MESSAGE = "노래 투하!";

    public static final BigDecimal DEFAULT_BASE_LATITUDE = PYRAMID_OF_KHUFU_LATITUDE;
    public static final BigDecimal DEFAULT_BASE_LONGITUDE = PYRAMID_OF_KHUFU_LONGITUDE;

    public static BalloonBuilder youtubeMusicBalloonBuilder(YoutubeMusic youtubeMusic, User creator) {
        return Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(youtubeMusic)
                .creator(creator)
                .baseLatitude(DEFAULT_BASE_LATITUDE)
                .baseLongitude(DEFAULT_BASE_LONGITUDE)
                .message(DEFAULT_MESSAGE);
    }
}
