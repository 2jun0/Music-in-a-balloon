package com.musicinaballoon.music.application;

import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.external.SpotifyApi;
import com.musicinaballoon.music.external.response.SpotifyAlbum;
import com.musicinaballoon.music.external.response.SpotifyImage;
import com.musicinaballoon.music.external.response.SpotifyTrack;
import com.musicinaballoon.music.repository.SpotifyMusicRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotifyMusicServiceTest {

    @InjectMocks
    private SpotifyMusicService spotifyMusicService;

    @Mock
    private SpotifyMusicRepository spotifyMusicRepository;

    @Mock
    private SpotifyApi spotifyApi;

    @DisplayName("스포티파이 음악을 외부 API에서 조회한다")
    @Test
    void getSpotifyMusicFromApi() {
        // given
        given(spotifyMusicRepository.findBySpotifyId(anyString())).willReturn(Optional.empty());
        given(spotifyMusicRepository.save(any(SpotifyMusic.class))).will(returnsFirstArg());

        SpotifyTrack track = new SpotifyTrack(SPOTIFY_MUSIC_SUPER_SHY_ID, SPOTIFY_MUSIC_SUPER_SHY_TITLE,
                new SpotifyAlbum(List.of(new SpotifyImage(SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL))));

        given(spotifyApi.getTrack(anyString())).willReturn(track);

        // when
        SpotifyMusic gotten = spotifyMusicService.getSpotifyMusic(SPOTIFY_MUSIC_SUPER_SHY_ID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(gotten.getSpotifyId()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_ID);
            softly.assertThat(gotten.getTitle()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_TITLE);
            softly.assertThat(gotten.getAlbumImageUrl()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL);
        });
    }

    @DisplayName("존재하는 스포티파이 음악을 조회한다")
    @Test
    void getSpotifyMusicExisted() {
        // given
        SpotifyMusic spotifyMusic = SpotifyMusic.builder()
                .spotifyId(SPOTIFY_MUSIC_SUPER_SHY_ID)
                .title(SPOTIFY_MUSIC_SUPER_SHY_TITLE)
                .albumImageUrl(SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL)
                .build();
        given(spotifyMusicRepository.findBySpotifyId(anyString())).willReturn(Optional.of(spotifyMusic));

        // when
        SpotifyMusic gotten = spotifyMusicService.getSpotifyMusic(SPOTIFY_MUSIC_SUPER_SHY_ID);

        // then
        assertThat(gotten).isEqualTo(spotifyMusic);
    }
}