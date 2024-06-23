package com.musicinaballoon.wave.application;

import com.musicinaballoon.wave.domain.Wave;
import com.musicinaballoon.wave.repository.WaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaveService {

    private final WaveRepository waveRepository;

    public Wave getCurrentWave() {
        return waveRepository.findById(1L).orElseThrow();
    }
}
