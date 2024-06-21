package com.musicinaballoon.geolocation.presentation;

import static com.musicinaballoon.fixture.IpAddressFixture.DEFAULT_IP_ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.common.util.RequestUtil;
import com.musicinaballoon.geolocation.application.response.GeolocationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GeolocationControllerTest extends IntegrationTest {

    private static ExtractableResponse<Response> getGeolocation(String ip) {
        return RestAssured
                .given()
                .header(RequestUtil.IP_RELATED_HEADERS.getFirst(), ip)
                .when()
                .get("/geolocation")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("위치 정보를 성공적으로 받아온다")
    void getGeolocationSuccess() {
        // when
        ExtractableResponse<Response> response = getGeolocation(DEFAULT_IP_ADDRESS);
        GeolocationResponse geolocationResponse = response.as(GeolocationResponse.class);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(geolocationResponse.latitude()).isNotNull();
            softly.assertThat(geolocationResponse.latitude()).isNotNull();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"255.255.255.255", "Do love python!"})
    @DisplayName("잘못된 IP 주소의 위치 정보를 받아올때는 ServiceUnavailable 예외가 발생한다")
    void getGeolocationWithInvalidIPAddress(String invalidIpAddr) {
        // when
        ExtractableResponse<Response> response = getGeolocation(invalidIpAddr);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }
}