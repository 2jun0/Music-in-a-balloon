package com.musicinaballoon.common.util;

import java.math.BigDecimal;

public final class GeolocationUtil {

    public static double EARTH_RADIUS_KILOMETER = 6372.8;

    /**
     * Get distance between two geographical points on earth. see https://rosettacode.org/wiki/Haversine_formula.
     */
    public static double distanceBetween(BigDecimal latitudeDegree1, BigDecimal longitudeDegree1, BigDecimal latitudeDegree2,
            BigDecimal longitudeDegree2) {
        double latitudeRadian1 = Math.toRadians(latitudeDegree1.doubleValue());
        double latitudeRadian2 = Math.toRadians(latitudeDegree2.doubleValue());

        double latitudeDifference = latitudeRadian2 - latitudeRadian1;
        double longitudeDifference = Math.toRadians(longitudeDegree2.doubleValue() - longitudeDegree1.doubleValue());

        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.pow(Math.sin(longitudeDifference / 2), 2) * Math.cos(
                latitudeRadian1) * Math.cos(latitudeRadian2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS_KILOMETER * c;
    }
}
