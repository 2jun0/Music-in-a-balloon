package com.musicinaballoon.geolocation.application.response;

import com.musicinaballoon.geolocation.external.response.IpGeolocation;
import java.math.BigDecimal;

public record GeolocationResponse(
        BigDecimal latitude,
        BigDecimal longitude
) {

    public static GeolocationResponse from(IpGeolocation ipGeolocation) {
        BigDecimal lat = ipGeolocation.lat();
        BigDecimal lon = ipGeolocation.lon();

        if (lat == null || lon == null) {
            throw new IllegalArgumentException("ipGeolocation's lat or lon is null");
        }

        return new GeolocationResponse(lat, lon);
    }
}
