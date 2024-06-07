package com.musicinaballoon.music.application;

import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.StreamingMusicUrlParser;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.domain.YoutubeMusicUrlParser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MusicServiceTest {

    MusicService musicService;

    @Mock
    YoutubeMusicService youtubeMusicService;

    @Mock
    SpotifyMusicService spotifyMusicService;

    @Mock
    StreamingMusicUrlParser spotifyMusicUrlParser;

    @Mock
    YoutubeMusicUrlParser youtubeMusicUrlParser;

    @BeforeEach
    void setUp() {
        given(spotifyMusicUrlParser.type()).willReturn(StreamingMusicType.SPOTIFY_MUSIC);
        given(youtubeMusicUrlParser.type()).willReturn(StreamingMusicType.YOUTUBE_MUSIC);
        musicService = new MusicService(List.of(spotifyMusicUrlParser, youtubeMusicUrlParser),
                youtubeMusicService, spotifyMusicService);
    }

    @DisplayName("유튜브 뮤직 url의 스트리밍 음악 타입은 유튜브 음악이다")
    @Test
    void checkStreamingMusicTypeOfYoutubeUrl() {
        // given
        given(youtubeMusicUrlParser.check(anyString())).willReturn(true);

        // when
        StreamingMusicType streamingMusicType = musicService.checkStreamingMusicType(YOUTUBE_MUSIC_SUPER_SHY_URL);

        // then
        assertThat(streamingMusicType).isEqualTo(StreamingMusicType.YOUTUBE_MUSIC);
    }

    @DisplayName("스포티파이 뮤직 url의 스트리밍 음악 타입은 스포티파이 음악이다")
    @Test
    void checkStreamingMusicTypeOfSpotifyUrl() {
        // given
        given(spotifyMusicUrlParser.check(anyString())).willReturn(true);

        // when
        StreamingMusicType streamingMusicType = musicService.checkStreamingMusicType(SPOTIFY_MUSIC_SUPER_SHY_URL);

        // then
        assertThat(streamingMusicType).isEqualTo(StreamingMusicType.SPOTIFY_MUSIC);
    }

    @DisplayName("잘못된 url의 스트리밍 음악 타입 조회시 BadReqeust 예외를 던진다")
    @Test
    void checkStreamingMusicTypeOfInvalidUrl() {
        // when & then
        assertThatThrownBy(() -> musicService.checkStreamingMusicType("invalid url")).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("유튜브 음악 url로 유튜브 음악을 조회한다")
    @Test
    void getYoutubeMusicByUrl() {
        // given
        YoutubeMusic youtubeMusic = mock(YoutubeMusic.class);
        given(youtubeMusicService.getYoutubeMusic(anyString())).willReturn(youtubeMusic);
        given(youtubeMusicUrlParser.extractId(anyString())).willReturn(anyString());

        // when
        YoutubeMusic gotton = musicService.getYoutubeMusicByUrl(YOUTUBE_MUSIC_SUPER_SHY_ID);

        // then
        assertThat(gotton).isEqualTo(youtubeMusic);
    }

    @DisplayName("유튜브 음악 url로 유튜브 음악을 조회한다")
    @Test
    void getSpotifyMusicByUrl() {
        // given
        SpotifyMusic spotifyMusic = mock(SpotifyMusic.class);
        given(spotifyMusicService.getSpotifyMusic(anyString())).willReturn(spotifyMusic);
        given(spotifyMusicUrlParser.extractId(anyString())).willReturn(anyString());

        // when
        SpotifyMusic gotton = musicService.getSpotifyMusicByUrl(SPOTIFY_MUSIC_SUPER_SHY_URL);

        // then
        assertThat(gotton).isEqualTo(spotifyMusic);
    }
}