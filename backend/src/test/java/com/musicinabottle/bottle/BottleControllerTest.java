package com.musicinabottle.bottle;

import static com.musicinabottle.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinabottle.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinabottle.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinabottle.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinabottle.fixture.PositionFixture.PYRAMID_OF_KHUFU_LAT;
import static com.musicinabottle.fixture.PositionFixture.PYRAMID_OF_KHUFU_LON;
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
        CreateBottleRequest request = new CreateBottleRequest(YOUTUBE_MUSIC_SUPER_SHY_URL, PYRAMID_OF_KHUFU_LAT, PYRAMID_OF_KHUFU_LON);

        ExtractableResponse<Response> response = postBottle(request);
        BottleResponse bottleResponse = response.as(BottleResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(bottleResponse.id()).isNotNull();
                    softly.assertThat(bottleResponse.title()).isEqualTo(YOUTUBE_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(bottleResponse.uploadedStreamingMusicType()).isEqualTo(StreamingMusicType.YOUTUBE_MUSIC.name());
                    softly.assertThat(bottleResponse.albumImageUrl()).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("스포티파이 음악 URL로 병을 생성한다")
    void createBottleBySpotifyMusicUrl() {
        CreateBottleRequest request = new CreateBottleRequest(SPOTIFY_MUSIC_SUPER_SHY_URL, PYRAMID_OF_KHUFU_LAT, PYRAMID_OF_KHUFU_LON);

        ExtractableResponse<Response> response = postBottle(request);
        BottleResponse bottleResponse = response.as(BottleResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(bottleResponse.id()).isNotNull();
                    softly.assertThat(bottleResponse.title()).isEqualTo(SPOTIFY_MUSIC_SUPER_SHY_TITLE);
                    softly.assertThat(bottleResponse.uploadedStreamingMusicType()).isEqualTo(StreamingMusicType.SPOTIFY_MUSIC.name());
                    softly.assertThat(bottleResponse.albumImageUrl()).isNotNull();
                }
        );
    }
}