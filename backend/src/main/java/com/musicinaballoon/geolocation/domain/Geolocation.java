package com.musicinaballoon.geolocation.domain;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record Geolocation(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
