package com.musicinaballoon.geolocation.presentation;

import static com.musicinaballoon.common.util.RequestUtil.getClientIpAddress;

import com.musicinaballoon.geolocation.application.GeolocationService;
import com.musicinaballoon.geolocation.application.response.GeolocationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeolocationController {

    private final GeolocationService geolocationService;

    @GetMapping("/geolocation")
    public ResponseEntity<GeolocationResponse> getGeolocation(HttpServletRequest request) {
        String ip = getClientIpAddress(request);
        GeolocationResponse geolocation = geolocationService.getGeolocationByIp(ip);

        return ResponseEntity.ok(geolocation);
    }
}
