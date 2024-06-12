package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LAT;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LON;
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
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalloonServiceTest {

    public final User USER = User.builder()
            .name("username")
            .build();

    public final YoutubeMusic YOUTUBE_MUSIC_SUPER_SHY = YoutubeMusic.builder()
            .youtubeId(YOUTUBE_MUSIC_SUPER_SHY_ID)
            .title(YOUTUBE_MUSIC_SUPER_SHY_TITLE)
            .thumbnailUrl("https:/image.com/aaaaa")
            .build();

    public final SpotifyMusic SPOTIFY_MUSIC_SUPER_SHY = SpotifyMusic.builder()
            .spotifyId(SPOTIFY_MUSIC_SUPER_SHY_ID)
            .title(SPOTIFY_MUSIC_SUPER_SHY_TITLE)
            .albumImageUrl("https:/image.com/aaaaa")
            .build();

    @InjectMocks
    private BalloonService balloonService;

    @Mock
    private BalloonRepository balloonRepository;

    @DisplayName("유튜브 뮤직으로 풍선을 생성한다")
    @Test
    void createYoutubeMusicBalloon() {
        // given
        given(balloonRepository.save(any(Balloon.class))).will(returnsFirstArg());

        // when
        Balloon created = balloonService.createYoutubeMusicBalloon(YOUTUBE_MUSIC_SUPER_SHY, PYRAMID_OF_KHUFU_LAT,
                PYRAMID_OF_KHUFU_LON, USER);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(created.getUploadedStreamingMusicType()).isEqualTo(StreamingMusicType.YOUTUBE_MUSIC);
                    softly.assertThat(created.getYoutubeMusic()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY);
                    softly.assertThat(created.getBaseLon()).isEqualTo(PYRAMID_OF_KHUFU_LON);
                    softly.assertThat(created.getBaseLat()).isEqualTo(PYRAMID_OF_KHUFU_LAT);
                    softly.assertThat(created.getCreator()).isEqualTo(USER);
                }
        );
    }

    @DisplayName("스포티파이 뮤직으로 풍선을 생성한다")
    @Test
    void createSpotifyMusicBalloon() {
        // given
        given(balloonRepository.save(any(Balloon.class))).will(returnsFirstArg());

        // when
        Balloon created = balloonService.createSpotifyMusicBalloon(SPOTIFY_MUSIC_SUPER_SHY, PYRAMID_OF_KHUFU_LAT,
                PYRAMID_OF_KHUFU_LON, USER);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(created.getUploadedStreamingMusicType()).isEqualTo(StreamingMusicType.SPOTIFY_MUSIC);
                    softly.assertThat(created.getSpotifyMusic()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY);
                    softly.assertThat(created.getBaseLon()).isEqualTo(PYRAMID_OF_KHUFU_LON);
                    softly.assertThat(created.getBaseLat()).isEqualTo(PYRAMID_OF_KHUFU_LAT);
                    softly.assertThat(created.getCreator()).isEqualTo(USER);
                }
        );
    }

    @DisplayName("풍선을 가져온다")
    @Test
    void getBalloon() {
        // given
        Balloon balloon = Balloon.builder()
                .uploadedStreamingMusicType(StreamingMusicType.YOUTUBE_MUSIC)
                .youtubeMusic(YOUTUBE_MUSIC_SUPER_SHY)
                .baseLon(PYRAMID_OF_KHUFU_LON)
                .baseLat(PYRAMID_OF_KHUFU_LAT)
                .creator(USER)
                .build();

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
}