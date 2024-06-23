package com.musicinaballoon.geolocation.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Builder;

@Builder
public record IpGeolocation(
        Status status,
        Optional<BigDecimal> lat,
        Optional<BigDecimal> lon
) {
    public enum Status {
        @JsonProperty("success")
        SUCCESS,
        @JsonProperty("fail")
        FAIL
    }
}