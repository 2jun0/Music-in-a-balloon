package com.musicinabottle.user;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinabottle.IntegrationTest;
import com.musicinabottle.fixture.UserFixture;
import com.musicinabottle.user.request.CreateUserRequest;
import com.musicinabottle.user.response.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class UserControllerTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private static ExtractableResponse<Response> postUser(CreateUserRequest request) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/user")
                .then().log().all().extract();
    }

    private static ExtractableResponse<Response> getUserMe(Long userId) {
        return RestAssured
                .given()
                .cookie("userId", String.valueOf(userId))
                .when()
                .get("/user/me")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("유저 정보를 받아온다")
    void getMe() {
        var user = User.builder()
                .name(UserFixture.USERNAME_CAOCAO).build();
        userRepository.save(user);

        ExtractableResponse<Response> response = getUserMe(user.getId());
        UserResponse userResponse = response.as(UserResponse.class);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                    softly.assertThat(userResponse).isEqualTo(UserResponse.of(user));
                }
        );
    }

    @Test
    @DisplayName("유저를 생성하고 쿠키에 ID를 저장한다")
    void createUser() {
        CreateUserRequest request = new CreateUserRequest("username");
        ExtractableResponse<Response> response = postUser(request);

        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
                    softly.assertThat(response.cookie("userId")).isNotNull();
                }
        );

        User saved = userRepository.findById(Long.valueOf(response.cookie("userId"))).orElseThrow();
        assert saved.getName().equals(request.username());
    }
}