package com.musicinaballoon.balloon.presentation;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.balloon.application.response.BalloonListItemResponse;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.user.domain.User;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

@DisplayName("풍선 목록 조회")
public class GetBalloonPickedListTest extends BalloonControllerTest {

    @Value("${balloon.picked-list-page-size}")
    private int balloonPickedListPageSize;

    private ExtractableResponse<Response> getBalloonPickedList(int page) {
        return RestAssured
                .given()
                .cookie("userId", defaultUser.getId())
                .queryParam("page", page)
                .when()
                .get("/balloon/picked")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("유효한 요청을 받으면 풍선 여러개를 응답한다")
    void getBalloonPickedList_ValidRequest_ResponsesBalloonList() {
        // given
        YoutubeMusic youtubeMusic = createDefaultYoutubeMusic();
        List<Balloon> pickedBalloons = new ArrayList<>();
        User otherUser = userRepository.save(userBuilder().name("Other").build());

        for (int i = 0; i < balloonPickedListPageSize; i++) {
            Balloon balloon = balloonRepository.save(youtubeMusicBalloonBuilder(youtubeMusic, otherUser).build());
            balloonPickedRepository.save(createBalloonPicked(balloon));

            pickedBalloons.add(balloon);
        }

        for (int page = 0; page < 2; page++) {
            // when
            ExtractableResponse<Response> response = getBalloonPickedList(page);
            BalloonListResponse balloonListResponse = response.as(BalloonListResponse.class);

            // then
            assertSoftly(softly -> {
                softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                softly.assertThat(balloonListResponse.balloons())
                        .containsExactlyInAnyOrderElementsOf(pickedBalloons.stream().map(BalloonListItemResponse::from).toList());
            });
        }
    }
}
