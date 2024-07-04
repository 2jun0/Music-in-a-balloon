package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.common.util.GeolocationUtil.distanceBetween;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.balloon.repository.BalloonPickedRepository;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.geolocation.domain.Geolocation;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.domain.Wave;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BalloonPickService {

    private final BalloonPickedRepository balloonPickedRepository;
    private final double balloonPickReachKilometerLimit;

    public BalloonPickService(BalloonPickedRepository balloonPickedRepository,
            @Value("${balloon.pick-reach-kilometer-limit}") double balloonPickReachKilometerLimit) {
        this.balloonPickedRepository = balloonPickedRepository;
        this.balloonPickReachKilometerLimit = balloonPickReachKilometerLimit;
    }

    public void validateNotPicked(Long balloonId, Long pickerId) {
        boolean exists = balloonPickedRepository.existsByBalloonIdAndPickerId(balloonId, pickerId);
        if (exists) {
            throw new BadRequestException(ErrorCode.ALREADY_PICKED_BALLOON);
        }
    }

    public BalloonPicked pickBalloon(Balloon balloon, User picker) {
        validateNotPicked(balloon.getId(), picker.getId());

        return balloonPickedRepository.save(BalloonPicked.builder()
                .balloon(balloon)
                .picker(picker)
                .build());
    }

    public void validatePicked(Long balloonId, Long pickerId) {
        boolean exists = balloonPickedRepository.existsByBalloonIdAndPickerId(balloonId, pickerId);
        if (!exists) {
            throw new BadRequestException(ErrorCode.NOT_PICKED_BALLOON);
        }
    }

    public BalloonPicked getBalloonPicked(Long balloonId, Long pickerId) {
        return balloonPickedRepository.findByBalloonIdAndPickerId(balloonId, pickerId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_PICKED_BALLOON));
    }

    public void validateBalloonInReach(BigDecimal userLatitude, BigDecimal userLongitude, Balloon balloon, Wave wave) {
        Geolocation currentBalloonGeolocation = balloon.getCurrentGeolocation(wave);

        double distance = distanceBetween(currentBalloonGeolocation.latitude(), userLatitude,
                currentBalloonGeolocation.longitude(),
                userLongitude);

        if (distance > balloonPickReachKilometerLimit) {
            throw new BadRequestException(ErrorCode.TOO_FAR_TO_PICK_BALLOON);
        }
    }
}
