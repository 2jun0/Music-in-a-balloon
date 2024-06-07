package com.musicinaballoon.music.application;

import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.external.YoutubeApi;
import com.musicinaballoon.music.external.reponse.YoutubeThumbnail;
import com.musicinaballoon.music.external.reponse.YoutubeVideo;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YoutubeMusicServiceTest {

    static final String ALBUM_URL = "https://image.com/aaaa";
    static final String VALID_VIDEO_DESCRIPTION = "Provided to YouTube";

    @InjectMocks
    private YoutubeMusicService youtubeMusicService;

    @Mock
    private YoutubeMusicRepository youtubeMusicRepository;

    @Mock
    private YoutubeApi youtubeApi;

    @DisplayName("유튜브 음악을 외부 API에서 조회한다")
    @Test
    void getYoutubeMusicFromApi() {
        // given
        given(youtubeMusicRepository.findByYoutubeId(anyString())).willReturn(Optional.empty());
        given(youtubeMusicRepository.save(any(YoutubeMusic.class))).will(returnsFirstArg());

        YoutubeVideo video = new YoutubeVideo(YOUTUBE_MUSIC_SUPER_SHY_ID, YOUTUBE_MUSIC_SUPER_SHY_TITLE, VALID_VIDEO_DESCRIPTION,
                new YoutubeThumbnail(ALBUM_URL));

        given(youtubeApi.getVideo(anyString())).willReturn(video);

        // when
        YoutubeMusic gotten = youtubeMusicService.getYoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_ID);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(gotten.getYoutubeId()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_ID);
                    softly.assertThat(gotten.getTitle()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(gotten.getThumbnailUrl()).isEqualTo(ALBUM_URL);
                }
        );
    }

    @DisplayName("유튜브 음악의 설명이 잘못된 경우 BadRequest 예외를 발생시킨다")
    @Test
    void getYoutubeMusicFromApiWithInvalidDes() {
        // given
        given(youtubeMusicRepository.findByYoutubeId(anyString())).willReturn(Optional.empty());

        YoutubeVideo video = new YoutubeVideo(YOUTUBE_MUSIC_SUPER_SHY_ID, YOUTUBE_MUSIC_SUPER_SHY_TITLE, "invalid description",
                new YoutubeThumbnail(ALBUM_URL));

        given(youtubeApi.getVideo(anyString())).willReturn(video);

        // when & then
        assertThatThrownBy(() -> youtubeMusicService.getYoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_ID)).isInstanceOf(
                BadRequestException.class);
    }

    @DisplayName("존재하는 유튜브 음악을 조회한다")
    @Test
    void getYoutubeMusicExisted() {
        // given
        YoutubeMusic youtubeMusic = YoutubeMusic.builder()
                .youtubeId(YOUTUBE_MUSIC_SUPER_SHY_ID)
                .title(YOUTUBE_MUSIC_SUPER_SHY_TITLE)
                .thumbnailUrl(ALBUM_URL)
                .build();
        given(youtubeMusicRepository.findByYoutubeId(anyString())).willReturn(Optional.of(youtubeMusic));

        // when
        YoutubeMusic gotton = youtubeMusicService.getYoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_ID);

        // then
        assertThat(gotton).isEqualTo(youtubeMusic);
    }
}