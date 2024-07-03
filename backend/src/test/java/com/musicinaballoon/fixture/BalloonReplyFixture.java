package com.musicinaballoon.fixture;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReply;
import com.musicinaballoon.balloon.domain.BalloonReply.BalloonReplyBuilder;
import com.musicinaballoon.user.domain.User;

public final class BalloonReplyFixture {

    public static final String DEFAULT_REPLY_MESSAGE = "너무 좋은 노래네요 :)";

    public static BalloonReplyBuilder balloonReplyBuilder(Balloon balloon, User replier) {
        return BalloonReply.builder()
                .balloon(balloon)
                .message(DEFAULT_REPLY_MESSAGE)
                .replier(replier);
    }
}
