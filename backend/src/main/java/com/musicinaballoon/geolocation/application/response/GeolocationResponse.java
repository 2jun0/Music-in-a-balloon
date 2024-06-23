package com.musicinaballoon.geolocation.application.response;

import com.musicinaballoon.geolocation.external.response.IpGeolocation;
import java.math.BigDecimal;

public record GeolocationResponse(
        BigDecimal latitude,
        BigDecimal longitude
) {

    public static GeolocationResponse from(IpGeolocation ipGeolocation) {
        BigDecimal lat = ipGeolocation.lat().orElseThrow();
        BigDecimal lon = ipGeolocation.lon().orElseThrow();

        return new GeolocationResponse(lat, lon);
    }
}
