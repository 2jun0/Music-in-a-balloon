package com.musicinaballoon.geolocation.external;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.ServiceUnavailableException;
import com.musicinaballoon.geolocation.external.response.IpGeolocation;
import org.springframework.http.HttpStatusCode;
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
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new BadRequestException(ErrorCode.INVALID_IP_ADDRESS);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ServiceUnavailableException(ErrorCode.IP_GEOLOCATION_UNAVAILABLE);
                })
                .body(IpGeolocation.class);
    }
}
