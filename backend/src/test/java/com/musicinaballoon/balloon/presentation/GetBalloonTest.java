package com.musicinaballoon.balloon.presentation;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.domain.YoutubeMusic;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("풍선 조회하기")
public class GetBalloonTest extends BalloonControllerTest {

    private ExtractableResponse<Response> getBalloon(Long balloonId) {
        return RestAssured
                .given()
                .pathParam("balloonId", balloonId)
                .when()
                .get("/balloon/{balloonId}")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("풍선을 성공적으로 조회한다")
    void getBalloonSuccess() {
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);

        ExtractableResponse<Response> response = getBalloon(balloon.getId());
        BalloonResponse balloonResponse = response.as(BalloonResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                    softly.assertThat(balloonResponse.id()).isEqualTo(balloon.getId());
                    softly.assertThat(balloonResponse.title()).isEqualTo(balloon.getMusicTitle());
                    softly.assertThat(balloonResponse.uploadedStreamingMusicType())
                            .isEqualTo(balloon.getUploadedStreamingMusicType().toString());
                    softly.assertThat(balloonResponse.baseLat()).isEqualTo(balloon.getBaseLat());
                    softly.assertThat(balloonResponse.baseLon()).isEqualTo(balloon.getBaseLon());
                }
        );
    }

    @Test
    @DisplayName("존재하지 않는 풍선을 조회하면 404 응답을 반환한다")
    void getBalloonNotExisted() {
        ExtractableResponse<Response> response = getBalloon(1L);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
                }
        );
    }
}
