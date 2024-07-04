package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.balloon.repository.BalloonPickedRepository;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalloonPickService {

    private final BalloonPickedRepository balloonPickedRepository;

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
}
