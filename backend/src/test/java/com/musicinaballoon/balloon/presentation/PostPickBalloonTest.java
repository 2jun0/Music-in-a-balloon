package com.musicinaballoon.balloon.presentation;

import static com.musicinaballoon.fixture.PositionFixture.EIFFEL_TOWER_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.EIFFEL_TOWER_LONGITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LATITUDE;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LONGITUDE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.fixture.BalloonFixture;
import com.musicinaballoon.music.domain.YoutubeMusic;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("풍선 줍기")
public class PostPickBalloonTest extends BalloonControllerTest {

    private ExtractableResponse<Response> postPickBalloon(Long balloonId, Long userId, PickBalloonRequest request) {
        return RestAssured
                .given()
                .cookie("userId", userId)
                .pathParam("balloonId", balloonId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/balloon/{balloonId}/pick")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("풍선을 성공적으로 줍는다")
    void pickBalloonSuccess() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        PickBalloonRequest request = new PickBalloonRequest(PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE);

        // when
        ExtractableResponse<Response> response = postPickBalloon(balloon.getId(), defaultUser.getId(), request);
        BalloonResponse balloonResponse = response.as(BalloonResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                    softly.assertThat(balloonResponse).isEqualTo(BalloonResponse.from(balloon));
                }
        );
    }

    @Test
    @DisplayName("같은 풍선을 중복으로 주울시 400 에러 코드를 반환한다")
    void pickBalloonDuplicate() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        PickBalloonRequest request = new PickBalloonRequest(PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE);

        // when
        ExtractableResponse<Response> response1 = postPickBalloon(balloon.getId(), defaultUser.getId(), request);
        ExtractableResponse<Response> response2 = postPickBalloon(balloon.getId(), defaultUser.getId(), request);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response1.statusCode()).isEqualTo(HttpStatus.SC_OK);
                    softly.assertThat(response2.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
                }
        );
    }

    @Test
    @DisplayName("너무 멀리 있는 풍선을 주울 시 400 에러 코드를 반환한다")
    void pickTooFarBalloon() {
        // given
        Balloon balloon = createTooFarBalloon();
        PickBalloonRequest request = new PickBalloonRequest(PYRAMID_OF_KHUFU_LATITUDE,
                PYRAMID_OF_KHUFU_LONGITUDE);

        // when
        ExtractableResponse<Response> response = postPickBalloon(balloon.getId(), defaultUser.getId(), request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    private Balloon createTooFarBalloon() {
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();

        return balloonRepository.save(BalloonFixture.youtubeMusicBalloonBuilder(youtubeMusic, defaultUser)
                .baseLatitude(EIFFEL_TOWER_LATITUDE)
                .baseLongitude(EIFFEL_TOWER_LONGITUDE)
                .build());
    }
}
