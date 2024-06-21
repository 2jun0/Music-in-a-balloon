package com.musicinaballoon.common.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public final class RequestUtil {

    public static List<String> IP_RELATED_HEADERS = List.of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");

    public static String getClientIpAddress(final HttpServletRequest request) {
        for (String headerName : IP_RELATED_HEADERS) {
            String ip = request.getHeader(headerName);

            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }
}
