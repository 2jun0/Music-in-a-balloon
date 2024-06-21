package com.musicinaballoon.geolocation.application;

import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.ServiceUnavailableException;
import com.musicinaballoon.geolocation.application.response.GeolocationResponse;
import com.musicinaballoon.geolocation.external.IpApi;
import com.musicinaballoon.geolocation.external.response.IpGeolocation;
import com.musicinaballoon.geolocation.external.response.IpGeolocation.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeolocationService {

    private final IpApi ipApi;

    public GeolocationResponse getGeolocationByIp(String ip) {
        IpGeolocation ipGeolocation = ipApi.getGeolocationByIp(ip);
        if (ipGeolocation.status().equals(Status.FAIL)) {
            throw new ServiceUnavailableException(ErrorCode.IP_GEOLOCATION_UNAVAILABLE);
        }

        return GeolocationResponse.from(ipGeolocation);
    }
}
