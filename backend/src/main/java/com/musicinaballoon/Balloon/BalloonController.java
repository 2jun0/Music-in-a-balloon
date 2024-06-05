package com.musicinaballoon.Balloon;

import com.musicinaballoon.Balloon.request.CreateBalloonRequest;
import com.musicinaballoon.Balloon.response.BalloonResponse;
import com.musicinaballoon.auth.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BalloonController {

    private final BalloonFacade balloonFacade;

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
