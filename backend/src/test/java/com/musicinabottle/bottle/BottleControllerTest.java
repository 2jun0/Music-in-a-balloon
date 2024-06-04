package com.musicinabottle.bottle;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinabottle.IntegrationTest;
import com.musicinabottle.bottle.request.CreateBottleRequest;
import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.streaming.StreamingMusicType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class BottleControllerTest extends IntegrationTest {

    private ExtractableResponse<Response> postBottle(CreateBottleRequest request) {
        return RestAssured
                .given()
                .cookie("userId", defaultUser.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/bottle")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("병을 줍는다")
    void pickBottle() {

    }

    @Test
    @DisplayName("유튜브 음악 URL로 병을 생성한다")
    void createBottleByYoutubeMusicUrl() {
        CreateBottleRequest request = new CreateBottleRequest("https://music.youtube.com/watch?v=n7ePZLn9_lQ");
        String title = "Super Shy";

        ExtractableResponse<Response> response = postBottle(request);
        BottleResponse bottleResponse = response.as(BottleResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(bottleResponse.id()).isNotNull();
                    softly.assertThat(bottleResponse.title()).isEqualTo(title);
                    softly.assertThat(bottleResponse.uploadedStreamingMusicType()).isEqualTo(StreamingMusicType.YOUTUBE_MUSIC.name());
                    softly.assertThat(bottleResponse.albumImageUrl()).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("스포티파이 음악 URL로 병을 생성한다")
    void createBottleBySpotifyMusicUrl() {
        CreateBottleRequest request = new CreateBottleRequest("https://open.spotify.com/track/5sdQOyqq2IDhvmx2lHOpwd");
        String title = "Super Shy";

        ExtractableResponse<Response> response = postBottle(request);
        BottleResponse bottleResponse = response.as(BottleResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(bottleResponse.id()).isNotNull();
                    softly.assertThat(bottleResponse.title()).isEqualTo(title);
                    softly.assertThat(bottleResponse.uploadedStreamingMusicType()).isEqualTo(StreamingMusicType.SPOTIFY_MUSIC.name());
                    softly.assertThat(bottleResponse.albumImageUrl()).isNotNull();
                }
        );
    }
}