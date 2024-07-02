package com.musicinaballoon.common.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class TimeUtil {

    public static ZonedDateTime utcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS);
    }

    public static long secondTimeDifference(ZonedDateTime a, ZonedDateTime b) {
        return Math.abs(ChronoUnit.SECONDS.between(a, b));
    }
}
