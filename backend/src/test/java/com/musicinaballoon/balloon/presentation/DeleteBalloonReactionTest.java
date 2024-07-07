package com.musicinaballoon.balloon.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.domain.YoutubeMusic;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("풍선 반응을 삭제하기")
public class DeleteBalloonReactionTest extends BalloonControllerTest {

    private ExtractableResponse<Response> deleteBalloonReaction(Long balloonId) {
        return RestAssured
                .given()
                .cookie("userId", defaultUser.getId())
                .pathParam("balloonId", balloonId)
                .when()
                .delete("/balloon/{balloonId}/reaction")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("올바른 요청이면 OK 를 응답한다.")
    void deleteBalloonReaction_ValidRequest_ResponsesOK() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        createBalloonPicked(balloon);
        createBalloonReaction(balloon);

        // when
        ExtractableResponse<Response> response = deleteBalloonReaction(balloon.getId());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("풍선에 반응하지 않았으면 NotFound 를 응답한다.")
    void deleteBalloonReaction_NotExistedBalloonReaction_ResponsesNotFound() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        Balloon balloon = createDefaultBalloon(youtubeMusic);
        createBalloonPicked(balloon);

        // when
        ExtractableResponse<Response> response = deleteBalloonReaction(balloon.getId());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
