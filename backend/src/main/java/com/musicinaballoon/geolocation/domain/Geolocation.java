package com.musicinaballoon.geolocation.domain;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record Geolocation(
        BigDecimal latitude,
        BigDecimal longitude
) {

    public static Geolocation of(double latitude, double longitude) {
        return new Geolocation(BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
    }

    public static Geolocation of(BigDecimal latitude, BigDecimal longitude) {
        return new Geolocation(latitude, longitude);
    }
}
