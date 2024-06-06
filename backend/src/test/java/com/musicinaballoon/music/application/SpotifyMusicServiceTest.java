package com.musicinaballoon.music.application;

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
import com.musicinaballoon.music.repository.SpotifyMusicRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

@ExtendWith(MockitoExtension.class)
class SpotifyMusicServiceTest {

    static final String ALBUM_URL = "https://image.com/aaaa";

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

        Track track = new Track.Builder()
                .setId(SPOTIFY_MUSIC_SUPER_SHY_ID)
                .setName(SPOTIFY_MUSIC_SUPER_SHY_TITLE)
                .setAlbum(
                        new AlbumSimplified.Builder()
                                .setImages(
                                        new Image.Builder().setUrl(ALBUM_URL).build()
                                ).build()
                ).build();

        given(spotifyApi.getTrack(anyString())).willReturn(track);

        // when
        SpotifyMusic gotten = spotifyMusicService.getSpotifyMusic(SPOTIFY_MUSIC_SUPER_SHY_ID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(gotten.getSpotifyId()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_ID);
            softly.assertThat(gotten.getTitle()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_TITLE);
            softly.assertThat(gotten.getAlbumImageUrl()).isEqualTo(ALBUM_URL);
        });
    }

    @DisplayName("존재하는 스포티파이 음악을 조회한다")
    @Test
    void getSpotifyMusicExisted() {
        // given
        SpotifyMusic spotifyMusic = SpotifyMusic.builder()
                .spotifyId(SPOTIFY_MUSIC_SUPER_SHY_ID)
                .title(SPOTIFY_MUSIC_SUPER_SHY_TITLE)
                .albumImageUrl(ALBUM_URL)
                .build();
        given(spotifyMusicRepository.findBySpotifyId(anyString())).willReturn(Optional.of(spotifyMusic));

        // when
        SpotifyMusic gotten = spotifyMusicService.getSpotifyMusic(SPOTIFY_MUSIC_SUPER_SHY_ID);

        // then
        assertThat(gotten).isEqualTo(spotifyMusic);
    }
}