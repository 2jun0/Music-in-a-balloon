package com.musicinaballoon.balloon.presentation;

import com.musicinaballoon.auth.presentation.UserId;
import com.musicinaballoon.balloon.application.BalloonFacade;
import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping(path = "/balloon/{balloonId}/pick")
    public BalloonResponse pickBalloon(@PathVariable("balloonId") Long balloonId,
            @Valid @RequestBody PickBalloonRequest request, @UserId Long userId) {
        return balloonFacade.pickBalloon(balloonId, userId, request);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/balloon")
    public BalloonResponse createBalloon(@Valid @RequestBody CreateBalloonRequest request, @UserId Long userId) {
        return balloonFacade.createBalloon(request, userId);
    }

    @GetMapping(path = "/balloon/list")
    public BalloonListResponse getBalloonList(@RequestParam(name = "page") Integer page) {
        return balloonFacade.getBalloonList(page);
    }
}
