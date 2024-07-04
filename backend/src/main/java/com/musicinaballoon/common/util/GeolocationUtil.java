package com.musicinaballoon.common.util;

import com.musicinaballoon.geolocation.domain.Geolocation;

public final class GeolocationUtil {

    public static double EARTH_RADIUS_KILOMETER = 6372.8;

    public static Geolocation toRadiansFromDegree(Geolocation geolocationDegree) {
        return Geolocation.of(
                Math.toRadians(geolocationDegree.latitude().doubleValue()),
                Math.toRadians(geolocationDegree.latitude().doubleValue())
        );
    }

    /**
     * Get distance between two geographical points on earth. see https://rosettacode.org/wiki/Haversine_formula.
     */
    public static double distanceBetween(Geolocation geolocationRadians1, Geolocation geolocationRadians2) {
        double latitudeDifference = geolocationRadians2.latitude().doubleValue() - geolocationRadians1.latitude().doubleValue();
        double longitudeDifference =
                geolocationRadians2.longitude().doubleValue() - geolocationRadians1.longitude().doubleValue();

        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.pow(Math.sin(longitudeDifference / 2), 2) * Math.cos(
                geolocationRadians1.latitude().doubleValue()) * Math.cos(geolocationRadians2.latitude().doubleValue());
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS_KILOMETER * c;
    }
}
