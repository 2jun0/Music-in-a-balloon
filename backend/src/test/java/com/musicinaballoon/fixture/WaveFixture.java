package com.musicinaballoon.fixture;

import com.musicinaballoon.wave.domain.Wave;
import com.musicinaballoon.wave.domain.Wave.WaveBuilder;

public final class WaveFixture {

    public static final Double DEFAULT_WAVE_VELOCITY = 0.001;
    public static final Double DEFAULT_WAVE_OFFSET_LON = 0.0;
    public static final Double DEFAULT_WAVE_AMPLITUDE = 0.125;
    public static final Double DEFAULT_WAVE_PERIOD = 10.0;

    public static WaveBuilder waveBuilder() {
        return Wave.builder()
                .velocity(DEFAULT_WAVE_VELOCITY)
                .offsetLon(DEFAULT_WAVE_OFFSET_LON)
                .amplitude(DEFAULT_WAVE_AMPLITUDE)
                .period(DEFAULT_WAVE_PERIOD);
    }
}
