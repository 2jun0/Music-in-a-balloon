package com.musicinabottle.bottle;

import com.musicinabottle.bottle.request.CreateBottleRequest;
import com.musicinabottle.bottle.response.BottleResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

@RequiredArgsConstructor
@RestController
public class BottleController {

    private final BottleFacade bottleFacade;

    @PostMapping(path = "/bottle/pick")
    public BottleResponse pickBottle() {
        return bottleFacade.pickRandomBottle();
    }

    @PostMapping(path = "/bottle")
    public BottleResponse createBottle(@RequestBody CreateBottleRequest request) throws IOException, ParseException, SpotifyWebApiException {
        return bottleFacade.createBottle(request.streamingMusicUrl());
    }
}
