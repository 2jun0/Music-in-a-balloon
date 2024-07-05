package com.musicinaballoon.balloon.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReactionType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("풍선에 반응하기")
public class PutBalloonReactionTest extends BalloonControllerTest {

    private ExtractableResponse<Response> putBalloonReaction(Long balloonId, ReactBalloonRequest request) {
        return RestAssured
                .given()
                .cookie("userId", defaultUser.getId())
                .pathParam("balloonId", balloonId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/balloon/{balloonId}/reaction")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("올바른 요청이면 OK 를 응답한다.")
    void putBalloonReaction_ValidRequest_ResponsesOK() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        createBalloonPicked(balloon);
        ReactBalloonRequest request = new ReactBalloonRequest(BalloonReactionType.BALLOON);

        // when
        ExtractableResponse<Response> response = putBalloonReaction(balloon.getId(), request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("풍선을 줍지 않았으면 BadRequest 를 응답한다.")
    void putBalloonReaction_NotPickedBalloon_ResponsesBadRequest() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        ReactBalloonRequest request = new ReactBalloonRequest(BalloonReactionType.BALLOON);

        // when
        ExtractableResponse<Response> response = putBalloonReaction(balloon.getId(), request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("풍선에 이미 반응했으면 OK 를 응답한다.")
    void putBalloonReaction_AlreadyReacted_ResponsesOK() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        createBalloonReaction(balloon);
        ReactBalloonRequest request = new ReactBalloonRequest(BalloonReactionType.BALLOON);

        // when
        ExtractableResponse<Response> response = putBalloonReaction(balloon.getId(), request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
