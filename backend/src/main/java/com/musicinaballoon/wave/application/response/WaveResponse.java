package com.musicinaballoon.wave.application.response;

import com.musicinaballoon.wave.domain.Wave;
import lombok.Builder;

@Builder
public record WaveResponse(
        Long id,
        Double period,
        Double amplitude,
        Double offsetLon,
        Double velocity) {

    public static WaveResponse from(Wave wave) {
        return WaveResponse.builder()
                .id(wave.getId())
                .period(wave.getPeriod())
                .amplitude(wave.getAmplitude())
                .offsetLon(wave.getOffsetLon())
                .velocity(wave.getVelocity())
                .build();
    }
}
