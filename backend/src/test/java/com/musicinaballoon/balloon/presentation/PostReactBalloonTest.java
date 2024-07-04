package com.musicinaballoon.balloon.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReactType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("풍선에 반응하기")
public class PostReactBalloonTest extends BalloonControllerTest {

    private ExtractableResponse<Response> postReactBalloon(Long balloonId, ReactBalloonRequest request) {
        return RestAssured
                .given()
                .cookie("userId", defaultUser.getId())
                .pathParam("balloonId", balloonId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/balloon/{balloonId}/react")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("올바른 요청이면 OK 를 응답한다.")
    void postReactBalloon_ValidRequest_ResponsesOK() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        ReactBalloonRequest request = new ReactBalloonRequest(BalloonReactType.BALLOON);

        // when
        ExtractableResponse<Response> response = postReactBalloon(balloon.getId(), request);
        BalloonResponse balloonResponse = response.as(BalloonResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
