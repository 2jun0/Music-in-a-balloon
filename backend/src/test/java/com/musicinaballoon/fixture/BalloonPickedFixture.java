package com.musicinaballoon.fixture;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.balloon.domain.BalloonPicked.BalloonPickedBuilder;
import com.musicinaballoon.user.domain.User;

public final class BalloonPickedFixture {

    public static final String DEFAULT_REPLY_MESSAGE = "너무 좋은 노래네요 :)";

    public static BalloonPickedBuilder balloonPickedBuilder(Balloon balloon, User picker) {
        return BalloonPicked.builder()
                .balloon(balloon)
                .replyMessage(DEFAULT_REPLY_MESSAGE)
                .picker(picker);
    }
}
