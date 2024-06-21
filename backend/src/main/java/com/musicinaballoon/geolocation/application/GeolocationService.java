package com.musicinaballoon.geolocation.application;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
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
        if (!ipGeolocation.status().equals(Status.SUCCESS)) {
            throw new BadRequestException(ErrorCode.INVALID_IP_ADDRESS);
        }

        return GeolocationResponse.from(ipGeolocation);
    }
}
