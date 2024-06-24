package com.musicinaballoon.balloon.application;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReply;
import com.musicinaballoon.balloon.repository.BalloonReplyRepository;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalloonReplyService {

    private final BalloonReplyRepository balloonReplyRepository;

    public BalloonReply createBalloonReply(Balloon balloon, User replier, String replyMessage) {
        validateAlreadyReply(balloon, replier);

        BalloonReply balloonReply = BalloonReply.builder()
                .balloon(balloon)
                .replier(replier)
                .message(replyMessage)
                .build();
        return balloonReplyRepository.save(balloonReply);
    }

    private void validateAlreadyReply(Balloon balloon, User replier) {
        boolean exists = balloonReplyRepository.existsByBalloonIdAndReplierId(balloon.getId(), replier.getId());
        if (exists) {
            throw new BadRequestException(ErrorCode.ALREADY_PICKED_BALLOON);
        }
    }
}
