package com.musicinaballoon.balloon.presentation;

import static com.musicinaballoon.balloon.application.BalloonService.BALLOON_PAGE_SIZE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.SPOTIFY_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_TITLE;
import static com.musicinaballoon.fixture.MusicFixture.YOUTUBE_MUSIC_SUPER_SHY_URL;
import static com.musicinaballoon.fixture.PositionFixture.EIFFEL_TOWER_LAT;
import static com.musicinaballoon.fixture.PositionFixture.EIFFEL_TOWER_LON;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LAT;
import static com.musicinaballoon.fixture.PositionFixture.PYRAMID_OF_KHUFU_LON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.repository.BalloonRepository;
import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.music.domain.StreamingMusicType;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    private ExtractableResponse<Response> getBalloonList(int page) {
        return RestAssured
                .given()
                .queryParam("page", page)
                .when()
                .get("/balloon/list")
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

    @Test
    @DisplayName("풍선 여러개를 페이지 단위로 불러온다")
    void getBalloonListSuccess() {
        // given
        YoutubeMusic youtubeMusic = new YoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_URL, YOUTUBE_MUSIC_SUPER_SHY_TITLE, null);
        youtubeMusicRepository.save(youtubeMusic);

        List<Balloon> balloons = new ArrayList<>();

        for (int i = 0; i < BALLOON_PAGE_SIZE * 1.5; i++) {
            balloons.add(new Balloon(StreamingMusicType.YOUTUBE_MUSIC, youtubeMusic, null, defaultUser,
                    EIFFEL_TOWER_LAT, EIFFEL_TOWER_LON));
        }

        balloonRepository.saveAll(balloons);
        balloons.sort(Comparator.comparing(BaseEntity::getCreatedAt).reversed());

        for (int page = 0; page < 2; page++) {
            // when
            ExtractableResponse<Response> response = getBalloonList(page);
            BalloonListResponse balloonListResponse = response.as(BalloonListResponse.class);

            // then
            final int curPage = page;
            assertSoftly(softly -> {
                softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                softly.assertThat(balloonListResponse.balloons())
                        .isEqualTo(balloons.stream().skip(curPage * BALLOON_PAGE_SIZE).limit(BALLOON_PAGE_SIZE)
                                .map(BalloonResponse::from).toList());
            });
        }
    }

//    @Test
//    @DisplayName("풍선 여러개는 생성 날짜 순으로 정렬되어 불러온다")
//    void getBalloonListShouldSortByCreatedAt() {
//        // given
//        YoutubeMusic youtubeMusic = new YoutubeMusic(YOUTUBE_MUSIC_SUPER_SHY_URL, YOUTUBE_MUSIC_SUPER_SHY_TITLE, null);
//        youtubeMusicRepository.save(youtubeMusic);
//
//        Balloon balloonOlder = new Balloon(StreamingMusicType.YOUTUBE_MUSIC, youtubeMusic, null, defaultUser,
//                EIFFEL_TOWER_LAT, EIFFEL_TOWER_LON);
//        balloonRepository.save(balloonOlder);
//
//        Balloon balloonNewer = new Balloon(StreamingMusicType.YOUTUBE_MUSIC, youtubeMusic, null, defaultUser,
//                EIFFEL_TOWER_LAT, EIFFEL_TOWER_LON);
//        balloonRepository.save(balloonNewer);
//
//        for (int i = 0; i < BALLOON_PAGE_SIZE * 1.5; i++) {
//            balloons.add();
//        }
//
//        balloonRepository.saveAll(balloons);
//
//        for (int page = 0; page < 2; page++) {
//            // when
//            ExtractableResponse<Response> response = getBalloonList(page);
//            BalloonListResponse balloonListResponse = response.as(BalloonListResponse.class);
//
//            // then
//            final int curPage = page;
//            assertSoftly(softly -> {
//                softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
//                softly.assertThat(balloonListResponse.balloons())
//                        .isEqualTo(balloons.stream().skip(curPage * BALLOON_PAGE_SIZE).limit(BALLOON_PAGE_SIZE)
//                                .map(BalloonResponse::from).toList());
//            });
//        }
//    }
}