package com.musicinaballoon.fixture;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.balloon.domain.BalloonReaction.BalloonReactionBuilder;
import com.musicinaballoon.balloon.domain.BalloonReactionType;
import com.musicinaballoon.user.domain.User;

public final class BalloonReactionFixture {

    public static BalloonReactionBuilder balloonReactionBuilder(Balloon balloon, User user, BalloonReactionType type) {
        return BalloonReaction.builder()
                .balloon(balloon)
                .user(user)
                .type(type);
    }
}
