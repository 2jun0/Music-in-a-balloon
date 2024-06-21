package com.musicinaballoon.geolocation.application;

import static com.musicinaballoon.fixture.IpAddressFixture.DEFAULT_IP_ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;

import com.musicinaballoon.geolocation.application.response.GeolocationResponse;
import com.musicinaballoon.geolocation.external.IpApi;
import com.musicinaballoon.geolocation.external.response.IpGeolocation;
import com.musicinaballoon.geolocation.external.response.IpGeolocation.Status;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeolocationServiceTest {

    @Mock
    private IpApi ipApi;

    @InjectMocks
    private GeolocationService geolocationService;

    @Test
    @DisplayName("IP 주소로 위치정보를 받아온다")
    void getGeolocationByIp() {
        // give
        IpGeolocation ipGeolocation = IpGeolocation.builder()
                .status(Status.SUCCESS)
                .lat(Optional.of(new BigDecimal("45.0")))
                .lon(Optional.of(new BigDecimal("90.0")))
                .build();
        given(ipApi.getGeolocationByIp(anyString())).willReturn(ipGeolocation);

        // when
        GeolocationResponse gotten = geolocationService.getGeolocationByIp(DEFAULT_IP_ADDRESS);

        // then
        assertThat(gotten).isEqualTo(GeolocationResponse.from(ipGeolocation));
    }
}