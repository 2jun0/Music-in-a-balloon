package com.musicinaballoon.balloon.presentation;

import com.musicinaballoon.auth.presentation.UserId;
import com.musicinaballoon.balloon.application.BalloonFacade;
import com.musicinaballoon.balloon.application.request.CreateBalloonRequest;
import com.musicinaballoon.balloon.application.request.PickBalloonRequest;
import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.application.response.BalloonListResponse;
import com.musicinaballoon.balloon.application.response.BalloonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BalloonController {

    private final BalloonFacade balloonFacade;

    @GetMapping(path = "/balloon/{balloonId}")
    public ResponseEntity<BalloonResponse> getBalloon(@PathVariable("balloonId") Long balloonId) {
        BalloonResponse balloon = balloonFacade.getBalloon(balloonId);
        return ResponseEntity.ok(balloon);
    }

    @PostMapping(path = "/balloon/{balloonId}/pick")
    public ResponseEntity<BalloonResponse> pickBalloon(@PathVariable("balloonId") Long balloonId,
            @Valid @RequestBody PickBalloonRequest request, @UserId Long userId) {
        BalloonResponse balloon = balloonFacade.pickBalloon(balloonId, userId, request);
        return ResponseEntity.ok(balloon);
    }

    @PostMapping(path = "/balloon/{balloonId}/react")
    public ResponseEntity<BalloonResponse> reactBalloon(@PathVariable("balloonId") Long balloonId,
            @Valid @RequestBody ReactBalloonRequest request, @UserId Long userId) {
        balloonFacade.reactBalloon(balloonId, request, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/balloon")
    public ResponseEntity<BalloonResponse> createBalloon(@Valid @RequestBody CreateBalloonRequest request, @UserId Long userId) {
        BalloonResponse balloon = balloonFacade.createBalloon(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(balloon);
    }

    @GetMapping(path = "/balloon/list")
    public ResponseEntity<BalloonListResponse> getBalloonList(@RequestParam(name = "page") Integer page, @UserId Long userId) {
        BalloonListResponse balloonList = balloonFacade.getBalloonList(userId, page);
        return ResponseEntity.ok(balloonList);
    }
}
