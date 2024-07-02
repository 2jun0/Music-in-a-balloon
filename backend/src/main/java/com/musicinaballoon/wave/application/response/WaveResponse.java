package com.musicinaballoon.wave.application.response;

import com.musicinaballoon.wave.domain.Wave;
import lombok.Builder;

@Builder
public record WaveResponse(
        Long id,
        Double period,
        Double amplitude,
        Double offsetLongitude,
        Double velocity) {

    public static WaveResponse from(Wave wave) {
        return WaveResponse.builder()
                .id(wave.getId())
                .period(wave.getPeriod())
                .amplitude(wave.getAmplitude())
                .offsetLongitude(wave.getOffsetLongitude())
                .velocity(wave.getVelocity())
                .build();
    }
}
