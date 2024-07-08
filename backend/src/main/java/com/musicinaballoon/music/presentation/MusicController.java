package com.musicinaballoon.music.presentation;

import com.musicinaballoon.music.application.MusicFacade;
import com.musicinaballoon.music.application.request.GetMusicParam;
import com.musicinaballoon.music.application.response.MusicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicFacade musicFacade;

    @GetMapping("/music")
    public ResponseEntity<MusicResponse> getMusic(@Valid @ModelAttribute GetMusicParam param) {
        MusicResponse musicResponse = musicFacade.getMusic(param);
        return ResponseEntity.ok().body(musicResponse);
    }
}
