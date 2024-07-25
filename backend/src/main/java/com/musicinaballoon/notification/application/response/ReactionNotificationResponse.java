package com.musicinaballoon.notification.application.response;

import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.balloon.domain.BalloonReactionType;
import com.musicinaballoon.notification.domain.ReactionNotification;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record ReactionNotificationResponse(
        Long id,
        Long receiverId,
        Long balloonId,
        BalloonReactionType reactionType,
        ZonedDateTime createdAt
) {

    public static ReactionNotificationResponse from(ReactionNotification reactionNotification) {
        BalloonReaction balloonReaction = reactionNotification.getBalloonReaction();

        return ReactionNotificationResponse.builder()
                .id(reactionNotification.getId())
                .receiverId(reactionNotification.getReceiver().getId())
                .balloonId(balloonReaction.getBalloon().getId())
                .reactionType(balloonReaction.getType())
                .createdAt(reactionNotification.getCreatedAt())
                .build();
    }
}
