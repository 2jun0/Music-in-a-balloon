package com.musicinaballoon.balloon.presentation;

import static com.musicinaballoon.fixture.BalloonFixture.DEFAULT_COLOR_CODE;
import static com.musicinaballoon.fixture.BalloonFixture.DEFAULT_MESSAGE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LONGITUDE;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.music.domain.StreamingMusicType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("풍선 생성하기")
public class PostBalloonTest extends BalloonControllerTest {

    private ExtractableResponse<Response> postBalloon(CreateBalloonRequest request) {
        return RestAssured
                .given()
                .cookie("userId", defaultUser.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/balloon")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("유튜브 음악 URL 의 유효한 요청은 풍선을 응답한다")
    void postBalloon_ValidRequestWithYoutubeMusicUrl_ResponsesBalloon() {
        CreateBalloonRequest request = new CreateBalloonRequest(YOUTUBE_MUSIC_SUPER_SHY_URL, PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE, DEFAULT_MESSAGE, DEFAULT_COLOR_CODE);

        ExtractableResponse<Response> response = postBalloon(request);
        BalloonResponse balloonResponse = response.as(BalloonResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(balloonResponse.id()).isNotNull();
                    softly.assertThat(balloonResponse.title()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(balloonResponse.uploadedStreamingMusicType())
                            .isEqualTo(StreamingMusicType.YOUTUBE_MUSIC.name());
                    softly.assertThat(balloonResponse.albumImageUrl()).isNotNull();
                    softly.assertThat(balloonResponse.colorCode()).isEqualTo(DEFAULT_COLOR_CODE);
                }
        );
    }

    @Test
    @DisplayName("스포티파이 음악 URL 의 유효한 요청은 풍선을 응답한다")
    void postBalloon_ValidRequestWithSpotifyMusicUrl_ResponsesBalloon() {
        CreateBalloonRequest request = new CreateBalloonRequest(SPOTIFY_MUSIC_SUPER_SHY_URL, PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE, DEFAULT_MESSAGE, DEFAULT_COLOR_CODE);

        ExtractableResponse<Response> response = postBalloon(request);
        BalloonResponse balloonResponse = response.as(BalloonResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(balloonResponse.id()).isNotNull();
                    softly.assertThat(balloonResponse.title()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(balloonResponse.uploadedStreamingMusicType())
                            .isEqualTo(StreamingMusicType.SPOTIFY_MUSIC.name());
                    softly.assertThat(balloonResponse.albumImageUrl()).isNotNull();
                    softly.assertThat(balloonResponse.colorCode()).isEqualTo(DEFAULT_COLOR_CODE);
                }
        );
    }
}
