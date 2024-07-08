package com.musicinaballoon.music.presentation;

import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_THUMBNAIL_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.music.domain.StreamingMusicType.SPOTIFY_MUSIC;
import static com.musicinaballoon.music.domain.StreamingMusicType.YOUTUBE_MUSIC;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.music.application.response.MusicResponse;
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
        MusicResponse musicResponse = response.as(MusicResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(musicResponse.title()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(musicResponse.url()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_URL);
                    softly.assertThat(musicResponse.albumImageUrl()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_THUMBNAIL_URL);
                    softly.assertThat(musicResponse.streamingType()).isEqualTo(YOUTUBE_MUSIC);
                }
        );
    }

    @Test
    @DisplayName("스포티파이 음악에 대한 요청을 받으면 스포티파이 음악을 응답한다.")
    void getMusic_StreamingUrlIsSpotifyMusicUrl_ResponsesSpotifyMusic() {
        // when
        ExtractableResponse<Response> response = getMusic(SPOTIFY_MUSIC_SUPER_SHY_URL);
        MusicResponse musicResponse = response.as(MusicResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(musicResponse.title()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(musicResponse.url()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_URL);
                    softly.assertThat(musicResponse.albumImageUrl()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL);
                    softly.assertThat(musicResponse.streamingType()).isEqualTo(SPOTIFY_MUSIC);
                }
        );
    }

    @Test
    @DisplayName("같은 요청을 두번 받으면 같은 음악을 응답한다.")
    void getMusic_SameRequestTwice_ResponsesSameMusics() {
        // when
        ExtractableResponse<Response> responseFirst = getMusic(YOUTUBE_MUSIC_SUPER_SHY_URL);
        MusicResponse musicResponseFirst = responseFirst.as(MusicResponse.class);
        ExtractableResponse<Response> responseSecond = getMusic(YOUTUBE_MUSIC_SUPER_SHY_URL);
        MusicResponse musicResponseSecond = responseSecond.as(MusicResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(responseFirst.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(responseSecond.statusCode()).isEqualTo(HttpStatus.OK.value());
                    softly.assertThat(musicResponseSecond).isEqualTo(musicResponseFirst);
                }
        );
    }
}
