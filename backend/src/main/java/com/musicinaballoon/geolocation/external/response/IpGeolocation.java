package com.musicinaballoon.geolocation.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record IpGeolocation(
        Status status,
        @Nullable
        BigDecimal lat,
        @Nullable
        BigDecimal lon
) {
    public enum Status {
        @JsonProperty("success")
        SUCCESS,
        @JsonProperty("fail")
        FAIL
    }
}