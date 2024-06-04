package com.musicinabottle.bottle;

import com.musicinabottle.auth.UserId;
import com.musicinabottle.bottle.request.CreateBottleRequest;
import com.musicinabottle.bottle.response.BottleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BottleController {

    private final BottleFacade bottleFacade;

    @PostMapping(path = "/bottle/pick")
    public BottleResponse pickBottle() {
        return bottleFacade.pickRandomBottle();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/bottle")
    public BottleResponse createBottle(@RequestBody CreateBottleRequest request, @UserId Long userId) {
        return bottleFacade.createBottle(request.streamingMusicUrl(), userId);
    }
}
