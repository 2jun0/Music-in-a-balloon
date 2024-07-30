package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.balloon.domain.BalloonReactionType;
import com.musicinaballoon.balloon.repository.BalloonReactionRepository;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.NotFoundException;
import com.musicinaballoon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalloonReactionService {

    private final BalloonReactionRepository balloonReactionRepository;

    public BalloonReaction createBalloonReaction(Balloon balloon, User user, BalloonReactionType type) {
        return balloonReactionRepository.save(BalloonReaction.builder()
                .balloon(balloon)
                .user(user)
                .type(type)
                .build());
    }

    public BalloonReaction getBalloonReaction(Long balloonId, Long userId) {
        return balloonReactionRepository.findByBalloonIdAndUserId(balloonId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BALLOON_REACTION_NOT_FOUND));
    }

    public void deleteBalloonReaction(Long balloonId, Long userId) {
        balloonReactionRepository.deleteByBalloonIdAndUserId(balloonId, userId);
    }

    public void validateBalloonReactionExisted(Long balloonId, Long userId) {
        boolean exists = balloonReactionRepository.existsByBalloonIdAndUserId(balloonId, userId);
        if (!exists) {
            throw new NotFoundException(ErrorCode.BALLOON_REACTION_NOT_FOUND);
        }
    }

    public boolean existsBalloonReaction(Long balloonId, Long userId) {
        return balloonReactionRepository.existsByBalloonIdAndUserId(balloonId, userId);
    }
}
