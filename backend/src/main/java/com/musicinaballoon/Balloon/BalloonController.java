package com.musicinaballoon.balloon;

import com.musicinaballoon.auth.UserId;
import com.musicinaballoon.balloon.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.response.BalloonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BalloonController {

    private final BalloonFacade balloonFacade;

    @GetMapping(path = "/balloon/{balloonId}")
    public BalloonResponse getBalloon(@PathVariable("balloonId") Long balloonId) {
        return balloonFacade.getBalloon(balloonId);
    }

    @PostMapping(path = "/balloon/pick")
    public BalloonResponse pickBalloon() {
        return balloonFacade.pickRandomBalloon();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/balloon")
    public BalloonResponse createBalloon(@Valid @RequestBody CreateBalloonRequest request, @UserId Long userId) {
        return balloonFacade.createBalloon(request, userId);
    }
}
