package com.musicinaballoon.balloon;

import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LAT;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.balloon.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.response.BalloonResponse;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class BalloonControllerTest extends IntegrationTest {

    @Autowired
    private YoutubeMusicRepository youtubeMusicRepository;

    @Autowired
    private BalloonRepository balloonRepository;

    private ExtractableResponse<Response> getBalloon(Long balloonId) {
        return RestAssured
                .given()
                .pathParam("balloonId", balloonId)
                .when()
                .get("/balloon/{balloonId}")
                .then().log().all().extract();
    }

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
    @DisplayName("풍선을 성공적으로 조회한다")
    void getBalloonSuccess() {
        YoutubeMusic youtubeMusic = new YoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_URL, YOUTUBE_MUSIC_SUPER_SHY_TITLE, null);
        youtubeMusicRepository.save(youtubeMusic);
        Balloon balloon = new Balloon(StreamingMusicType.YOUTUBE_MUSIC, youtubeMusic, null, defaultUser, PYRAMID_OF_KHUFU_LAT,
                PYRAMID_OF_KHUFU_LON);
        balloonRepository.save(balloon);

        ExtractableResponse<Response> response = getBalloon(balloon.getId());
        BalloonResponse balloonResponse = response.as(BalloonResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                    softly.assertThat(balloonResponse).isEqualTo(BalloonResponse.from(balloon));
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

    @Test
    @DisplayName("유튜브 음악 URL로 풍선을 생성한다")
    void createBalloonByYoutubeMusicUrl() {
        CreateBalloonRequest request = new CreateBalloonRequest(YOUTUBE_MUSIC_SUPER_SHY_URL, PYRAMID_OF_KHUFU_LAT,
                PYRAMID_OF_KHUFU_LON);

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
                }
        );
    }

    @Test
    @DisplayName("스포티파이 음악 URL로 풍선을 생성한다")
    void createBalloonBySpotifyMusicUrl() {
        CreateBalloonRequest request = new CreateBalloonRequest(SPOTIFY_MUSIC_SUPER_SHY_URL, PYRAMID_OF_KHUFU_LAT,
                PYRAMID_OF_KHUFU_LON);

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
                }
        );
    }
}