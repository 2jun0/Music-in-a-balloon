package com.musicinaballoon.fixture;

import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.notification.domain.ReactionNotification.ReactionNotificationBuilder;
import com.musicinaballoon.user.domain.User;

public final class ReactionNotificationFixture {

    public static ReactionNotificationBuilder reactionNotificationBuilder(BalloonReaction balloonReaction, User receiver) {
        return ReactionNotification.builder()
                .balloonReaction(balloonReaction)
                .receiver(receiver);
    }
}
