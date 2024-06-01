package com.musicinabottle.fixture;

import com.musicinabottle.user.User;

public final class UserFixture {

    public static final String USERNAME_CAOCAO = "caocao";
    public static final String USERNAME_LIUBEI = "유비";
    public static final String USERNAME_GUANYU = "关羽";

    public static final User user(String username) {
        return User.builder().name(username).build();
    }
}
