package com.musicinaballoon.common.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class TimeUtil {

    public static final double R = 6372.8; // In kilometers

    public static ZonedDateTime utcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS);
    }

    public static long mircoTimeDifference(ZonedDateTime start, ZonedDateTime end) {
        return ChronoUnit.MICROS.between(start, end);
    }
}
