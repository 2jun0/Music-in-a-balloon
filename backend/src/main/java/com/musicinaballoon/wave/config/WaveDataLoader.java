package com.musicinaballoon.wave.config;

import com.musicinaballoon.wave.domain.Wave;
import com.musicinaballoon.wave.repository.WaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local"})
@RequiredArgsConstructor
public class WaveDataLoader implements CommandLineRunner {

    public static final Double WAVE_VELOCITY = 0.001;
    public static final Double WAVE_OFFSET_LON = 0.0;
    public static final Double WAVE_AMPLITUDE = 0.125;
    public static final Double WAVE_PERIOD = 10.0;

    private final WaveRepository waveRepository;

    @Override
    public void run(String... args) {
        Wave wave = new Wave(WAVE_VELOCITY, WAVE_OFFSET_LON, WAVE_AMPLITUDE, WAVE_PERIOD);
        waveRepository.save(wave);
    }
}
