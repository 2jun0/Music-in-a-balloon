package com.musicinaballoon.wave.presentation;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.wave.application.response.WaveResponse;
import com.musicinaballoon.wave.config.WaveDataLoader;
import com.musicinaballoon.wave.repository.WaveRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WaveControllerTest extends IntegrationTest {

    @Autowired
    private WaveRepository waveRepository;

    private ExtractableResponse<Response> getWave() {
        return RestAssured
                .when()
                .get("/wave")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("경로를 성공적으로 조회한다")
    void getWaveSuccess() {
        // when
        ExtractableResponse<Response> response = getWave();
        WaveResponse waveResponse = response.as(WaveResponse.class);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
                    softly.assertThat(waveResponse.amplitude()).isEqualTo(WaveDataLoader.WAVE_AMPLITUDE);
                    softly.assertThat(waveResponse.offsetLon()).isEqualTo(WaveDataLoader.WAVE_OFFSET_LON);
                    softly.assertThat(waveResponse.period()).isEqualTo(WaveDataLoader.WAVE_PERIOD);
                    softly.assertThat(waveResponse.velocity()).isEqualTo(WaveDataLoader.WAVE_VELOCITY);
                }
        );
    }
}