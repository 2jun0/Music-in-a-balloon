package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.BalloonPickedFixture.balloonPickedBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;
import static com.musicinaballoon.fixture.PositionFixture.EIFFEL_TOWER_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.EIFFEL_TOWER_LONGITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LONGITUDE;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static com.musicinaballoon.fixture.WaveFixture.waveBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.balloon.repository.BalloonPickedRepository;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.domain.Wave;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalloonPickServiceTest {

    private final static double BALLOON_PICK_REACH_KILOMETER_LIMIT = 300.0;

    @Mock
    private BalloonPickedRepository balloonPickedRepository;

    private BalloonPickService balloonPickService;

    @BeforeEach
    void setUp() {
        balloonPickService = new BalloonPickService(balloonPickedRepository, BALLOON_PICK_REACH_KILOMETER_LIMIT);
    }

    @Test
    @DisplayName("validateNotPicked 는 풍선을 주웠으면 BadRequestException 을 던진다.")
    void validateNotPicked_HasPickedBalloon_ThrowsBadRequestException() {
        // given
        given(balloonPickedRepository.existsByBalloonIdAndPickerId(anyLong(), anyLong())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> balloonPickService.validateNotPicked(1L, 1L)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("pickBalloon 는 올바른 입력을 받으면 BalloonPicked 를 반환한다.")
    void pickBalloon_ValidInputs_ReturnsBalloonPicked() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user).build();

        given(balloonPickedRepository.save(any(BalloonPicked.class))).will(returnsFirstArg());

        // when
        BalloonPicked balloonPicked = balloonPickService.pickBalloon(balloon, user);

        // then
        assertSoftly(softly -> {
            softly.assertThat(balloonPicked.getBalloon()).isEqualTo(balloon);
            softly.assertThat(balloonPicked.getPicker()).isEqualTo(user);
        });
    }

    @Test
    @DisplayName("validatePicked 은 풍선을 줍지 않았으면 BadRequestException 을 던진다.")
    void validatePicked_NotPickedBalloon_ThrowsBadRequestException() {
        // given
        given(balloonPickedRepository.existsByBalloonIdAndPickerId(anyLong(), anyLong())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> balloonPickService.validatePicked(1L, 1L)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("getBalloonPicked 은 올바른 입력을 받으면 BalloonPicked 을 반환한다.")
    void getBalloonPicked_ValidInputs_ReturnsBalloonPicked() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user).build();
        BalloonPicked balloonPicked = balloonPickedBuilder(balloon, user).build();

        given(balloonPickedRepository.findByBalloonIdAndPickerId(anyLong(), anyLong())).willReturn(Optional.of(balloonPicked));

        // when
        BalloonPicked gotten = balloonPickService.getBalloonPicked(1L, 1L);

        // then
        assertThat(gotten).isEqualTo(balloonPicked);
    }

    @Test
    @DisplayName("validateBalloonInReach 는 풍선이 유저의 도달가능한 범위에 있을때 예외를 던지지 않는다.")
    void validateBalloonInReach_WithinReachOfUserGeolocation_NotThrowException() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user)
                .baseLatitude(PYRAMID_OF_KHUFU_LATITUDE).baseLongitude(PYRAMID_OF_KHUFU_LONGITUDE).build();
        balloon.prePersist();
        Wave wave = waveBuilder().build();

        // then & when
        Assertions.assertThatNoException().isThrownBy(() -> balloonPickService.validateBalloonInReach(PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE, balloon, wave));
    }

    @Test
    @DisplayName("validateBalloonInReach 는 풍선이 유저의 도달가능한 범위 밖에 있을때 BadRequestException 예외를 던진다.")
    void validateBalloonInReach_OutOfReachOfUserGeolocation_ThrowsBadRequestException() {
        // given
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        User user = userBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user)
                .baseLatitude(PYRAMID_OF_KHUFU_LATITUDE).baseLongitude(PYRAMID_OF_KHUFU_LONGITUDE).build();
        balloon.prePersist();
        Wave wave = waveBuilder().build();

        // then & when
        assertThatThrownBy(() -> balloonPickService.validateBalloonInReach(EIFFEL_TOWER_LATITUDE,
                EIFFEL_TOWER_LONGITUDE, balloon, wave)).isInstanceOf(BadRequestException.class);
    }
}