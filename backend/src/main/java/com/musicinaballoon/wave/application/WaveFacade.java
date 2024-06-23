package com.musicinaballoon.wave.application;

import com.musicinaballoon.wave.application.response.WaveResponse;
import com.musicinaballoon.wave.domain.Wave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class WaveFacade {

    private final WaveService waveService;

    public WaveResponse getWave() {
        Wave wave = waveService.getCurrentWave();
        return WaveResponse.from(wave);
    }
}
