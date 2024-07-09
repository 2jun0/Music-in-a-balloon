package com.musicinaballoon.music.presentation;

import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_ID;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_THUMBNAIL_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_SUPER_SHY_URL;
import static com.musicinaballoon.music.domain.StreamingMusicType.SPOTIFY_MUSIC;
import static com.musicinaballoon.music.domain.StreamingMusicType.YOUTUBE_MUSIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.music.application.response.MusicResponse;
import com.musicinaballoon.music.application.response.SpotifyMusicResponse;
import com.musicinaballoon.music.application.response.YoutubeMusicResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("음악 조회")
public class GetMusicTest extends MusicControllerTest {

    private static ExtractableResponse<Response> getMusic(String streamingUrl) {
        return RestAssured
                .given()
                .param("streamingUrl", streamingUrl)
                .when()
                .get("/music")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("유튜브 음악에 대한 요청을 받으면 유튜브 음악을 응답한다.")
    void getMusic_StreamingUrlIsYoutubeMusicUrl_ResponsesYoutubeMusic() {
        // when
        ExtractableResponse<Response> response = getMusic(YOUTUBE_MUSIC_SUPER_SHY_URL);
        YoutubeMusicResponse musicResponse = response.as(YoutubeMusicResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(musicResponse.getTitle()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(musicResponse.getUrl()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_URL);
                    softly.assertThat(musicResponse.getAlbumImageUrl()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_THUMBNAIL_URL);
                    softly.assertThat(musicResponse.getStreamingType()).isEqualTo(YOUTUBE_MUSIC);
                    softly.assertThat(musicResponse.getYoutubeUrl()).isEqualTo(YOUTUBE_SUPER_SHY_URL);
                    softly.assertThat(musicResponse.getYoutubeId()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_ID);
                }
        );
    }

    @Test
    @DisplayName("스포티파이 음악에 대한 요청을 받으면 스포티파이 음악을 응답한다.")
    void getMusic_StreamingUrlIsSpotifyMusicUrl_ResponsesSpotifyMusic() {
        // when
        ExtractableResponse<Response> response = getMusic(SPOTIFY_MUSIC_SUPER_SHY_URL);
        SpotifyMusicResponse musicResponse = response.as(SpotifyMusicResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(musicResponse.getTitle()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(musicResponse.getUrl()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_URL);
                    softly.assertThat(musicResponse.getAlbumImageUrl()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL);
                    softly.assertThat(musicResponse.getStreamingType()).isEqualTo(SPOTIFY_MUSIC);
                    softly.assertThat(musicResponse.getSpotifyId()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_ID);
                }
        );
    }

    @Test
    @DisplayName("같은 요청을 두번 받으면 같은 음악을 응답한다.")
    void getMusic_SameRequestTwice_ResponsesSameMusics() {
        // when
        ExtractableResponse<Response> responseFirst = getMusic(YOUTUBE_MUSIC_SUPER_SHY_URL);
        MusicResponse musicResponseFirst = responseFirst.as(YoutubeMusicResponse.class);
        ExtractableResponse<Response> responseSecond = getMusic(YOUTUBE_MUSIC_SUPER_SHY_URL);
        MusicResponse musicResponseSecond = responseSecond.as(YoutubeMusicResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(responseFirst.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(responseSecond.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(musicResponseSecond).isEqualTo(musicResponseFirst);
                }
        );
    }

    @Test
    @DisplayName("존재하지 않는 유튜브 음악에 대한 요청을 받으면 NotFound 를 응답한다.")
    void getMusic_StreamingUrlIsNotExistedYoutubeMusicUrl_ResponsesNotFound() {
        // when
        ExtractableResponse<Response> response = getMusic("https://music.youtube.com/watch?v=AAAAAAAAAAA");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("존재하지 않는 스포티파이 음악에 대한 요청을 받으면 NotFound 를 응답한다.")
    void getMusic_StreamingUrlIsNotExistedSpotifyMusicUrl_ResponsesNotFound() {
        // when
        ExtractableResponse<Response> response = getMusic("https://open.spotify.com/track/AAAAAAAAAAAAAAAAAA");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
