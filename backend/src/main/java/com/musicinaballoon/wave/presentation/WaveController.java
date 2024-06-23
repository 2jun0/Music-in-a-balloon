package com.musicinaballoon.wave.presentation;

import com.musicinaballoon.wave.application.WaveFacade;
import com.musicinaballoon.wave.application.response.WaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WaveController {

    private final WaveFacade waveFacade;

    @GetMapping("/wave")
    public ResponseEntity<WaveResponse> getWave() {
        WaveResponse waveResponse = waveFacade.getWave();
        return ResponseEntity.ok(waveResponse);
    }
}
