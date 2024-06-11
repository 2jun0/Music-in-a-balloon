package com.musicinaballoon.wave.domain;

import com.musicinaballoon.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity(name = "movement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wave extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period", nullable = false)
    private Double period;

    @Column(name = "amplitude", nullable = false)
    private Double amplitude;

    @Column(name = "offset_lon", nullable = false)
    private Double offset_lon;

    @Column(name = "velocity", nullable = false)
    private Double velocity;

    @Builder
    public Wave(
            @NonNull Double velocity,
            @NonNull Double offset_lon,
            @NonNull Double amplitude,
            @NonNull Double period) {
        this.velocity = velocity;
        this.offset_lon = offset_lon;
        this.amplitude = amplitude;
        this.period = period;
    }

    public double calcLon(double baseLon, long time) {
        return baseLon + velocity * time;
    }

    public double calcLat(double curLon, double baseLat, double baseLon) {
        return func(curLon) - baseLat + func(baseLon);
    }

    private double func(double lonDegree) {
        return amplitude * 90 * Math.sin(period * Math.toRadians(lonDegree + offset_lon));
    }
}
