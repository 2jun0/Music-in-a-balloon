package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.fixture.BalloonFixture.DEFAULT_COLOR_CODE;
import static com.musicinaballoon.fixture.BalloonFixture.DEFAULT_MESSAGE;
import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.MusicFixture.spotifyMusicBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LONGITUDE;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.repository.BalloonRepository;
import com.musicinaballoon.common.exception.NotFoundException;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BalloonServiceTest {

    private final static int BALLOON_LIST_PAGE_SIZE = 5;

    private BalloonService balloonService;

    @Mock
    private BalloonRepository balloonRepository;

    @BeforeEach
    void setUp() {
        balloonService = new BalloonService(balloonRepository, BALLOON_LIST_PAGE_SIZE);
    }

    @DisplayName("유튜브 뮤직으로 풍선을 생성한다")
    @Test
    void createBalloon_ValidInputsWithYoutubeMusic_ReturnsBalloon() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();

        given(balloonRepository.save(any(Balloon.class))).will(returnsFirstArg());

        // when
        Balloon created = balloonService.createBalloon(youtubeMusic, PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE, user, DEFAULT_MESSAGE, DEFAULT_COLOR_CODE);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(created.getUploadedStreamingMusicType()).isEqualTo(StreamingMusicType.YOUTUBE_MUSIC);
                    softly.assertThat(created.getYoutubeMusic()).isEqualTo(youtubeMusic);
                    softly.assertThat(created.getBaseLongitude()).isEqualTo(PYRAMID_OF_KHUFU_LONGITUDE);
                    softly.assertThat(created.getBaseLatitude()).isEqualTo(PYRAMID_OF_KHUFU_LATITUDE);
                    softly.assertThat(created.getCreator()).isEqualTo(user);
                    softly.assertThat(created.getColorCode()).isEqualTo(DEFAULT_COLOR_CODE);
                    softly.assertThat(created.getMessage()).isEqualTo(DEFAULT_MESSAGE);
                }
        );
    }

    @DisplayName("스포티파이 뮤직으로 풍선을 생성한다")
    @Test
    void createBalloon_ValidInputsWithSpotifyMusic_ReturnsBalloon() {
        // given
        SpotifyMusic spotifyMusic = spotifyMusicBuilder().build();
        User user = userBuilder().build();

        given(balloonRepository.save(any(Balloon.class))).will(returnsFirstArg());

        // when
        Balloon created = balloonService.createBalloon(spotifyMusic, PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE, user, DEFAULT_MESSAGE, DEFAULT_COLOR_CODE);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(created.getUploadedStreamingMusicType()).isEqualTo(StreamingMusicType.SPOTIFY_MUSIC);
                    softly.assertThat(created.getSpotifyMusic()).isEqualTo(spotifyMusic);
                    softly.assertThat(created.getBaseLongitude()).isEqualTo(PYRAMID_OF_KHUFU_LONGITUDE);
                    softly.assertThat(created.getBaseLatitude()).isEqualTo(PYRAMID_OF_KHUFU_LATITUDE);
                    softly.assertThat(created.getCreator()).isEqualTo(user);
                    softly.assertThat(created.getColorCode()).isEqualTo(DEFAULT_COLOR_CODE);
                    softly.assertThat(created.getMessage()).isEqualTo(DEFAULT_MESSAGE);
                }
        );
    }

    @DisplayName("풍선을 가져온다")
    @Test
    void getBalloon() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user).build();

        given(balloonRepository.findById(anyLong())).willReturn(Optional.of(balloon));

        // when
        Balloon gotten = balloonService.getBalloon(1L);

        // then
        assertThat(gotten).isEqualTo(balloon);
    }

    @DisplayName("없는 풍선을 가져올시 NotFound 예외가 발생한다")
    @Test
    void getBalloonNotFound() {
        // given
        given(balloonRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> balloonService.getBalloon(1L)).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("getNotPickedBalloonList 는 유효한 입력을 받으면 풍선리스트를 반환한다.")
    @Test
    void getNotPickedBalloonList_InvalidInputs_ReturnsBalloonList() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();

        List<Balloon> balloons = new ArrayList<>();
        for (int i = 0; i < BALLOON_LIST_PAGE_SIZE; i++) {
            balloons.add(youtubeMusicBalloonBuilder(youtubeMusic, user).build());
        }

        given(balloonRepository.findNotPickedNotCreatedByUserOrderByCreatedAtDesc(any(User.class),
                any(Pageable.class))).willReturn(balloons);

        // when
        List<Balloon> gotten = balloonService.getNotPickedBalloonList(user, 0);

        // then
        assertThat(gotten).isEqualTo(balloons);
    }
}