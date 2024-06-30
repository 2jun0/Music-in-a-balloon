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
@Entity(name = "wave")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wave extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period", nullable = false)
    private Double period;

    @Column(name = "amplitude", nullable = false)
    private Double amplitude;

    @Column(name = "offset_longitude", nullable = false)
    private Double offsetLongitude;

    @Column(name = "velocity", nullable = false)
    private Double velocity;

    @Builder
    public Wave(
            @NonNull Double velocity,
            @NonNull Double offsetLon,
            @NonNull Double amplitude,
            @NonNull Double period) {
        this.velocity = velocity;
        this.offsetLongitude = offsetLon;
        this.amplitude = amplitude;
        this.period = period;
    }

    public double calculateLongitude(double baseLongitude, long time) {
        return baseLongitude + velocity * time;
    }

    public double calculateLatitude(double currentLongitude, double baseLatitude, double baseLongitude) {
        return func(currentLongitude) + baseLatitude - func(baseLongitude);
    }

    private double func(double longitudeDegree) {
        return amplitude * 90 * Math.sin(period * Math.toRadians(longitudeDegree + offsetLongitude));
    }
}
