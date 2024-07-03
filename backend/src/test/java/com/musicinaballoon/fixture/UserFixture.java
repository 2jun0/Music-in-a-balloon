package com.musicinaballoon.fixture;

import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.user.domain.User.UserBuilder;

public final class UserFixture {

    public static final String DEFAULT_USERNAME = "default man";

    public static UserBuilder userBuilder() {
        return User.builder()
                .name(DEFAULT_USERNAME);
    }
}
