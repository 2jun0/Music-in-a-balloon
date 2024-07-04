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

    public BalloonPicked pickBalloon(Balloon balloon, User picker) {
        validateNotPicked(balloon, picker);

        return balloonPickedRepository.save(BalloonPicked.builder()
                .balloon(balloon)
                .picker(picker)
                .build());
    }

    private void validateNotPicked(Balloon balloon, User picker) {
        boolean exists = balloonPickedRepository.existsByBalloonAndPicker(balloon, picker);
        if (exists) {
            throw new BadRequestException(ErrorCode.ALREADY_PICKED_BALLOON);
        }
    }
}
