package com.musicinaballoon.geolocation.external;

import com.musicinaballoon.geolocation.external.response.IpGeolocation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class IpApi {

    public static String IP_API_BASE_URL = "http://ip-api.com";
    private final RestClient restClient = RestClient.create(IP_API_BASE_URL);

    public IpGeolocation getGeolocationByIp(String ip) {
        return restClient.get()
                .uri("/json/{id}?fields=status,lat,lon", ip)
                .retrieve()
                .body(IpGeolocation.class);
    }
}
