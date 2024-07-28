package com.musicinaballoon.notification.application.response;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.balloon.domain.BalloonReactionType;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.user.application.response.UserResponse;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record ReactionNotificationResponse(
        Long id,
        Long receiverId,
        BalloonResponse balloon,
        UserResponse responder,
        BalloonReactionType reactionType,
        ZonedDateTime createdAt
) {

    public static ReactionNotificationResponse from(ReactionNotification reactionNotification) {
        BalloonReaction balloonReaction = reactionNotification.getBalloonReaction();

        return ReactionNotificationResponse.builder()
                .id(reactionNotification.getId())
                .receiverId(reactionNotification.getReceiver().getId())
                .balloon(BalloonResponse.from(balloonReaction.getBalloon()))
                .reactionType(balloonReaction.getType())
                .createdAt(reactionNotification.getCreatedAt())
                .responder(UserResponse.from(balloonReaction.getUser()))
                .build();
    }

    @Builder
    record BalloonResponse(
            Long id,
            String title,
            String colorCode
    ) {

        static BalloonResponse from(Balloon balloon) {
            return BalloonResponse.builder()
                    .id(balloon.getId())
                    .title(balloon.getMusicTitle())
                    .colorCode(balloon.getColorCode())
                    .build();
        }
    }
}
