package com.musicinaballoon.music.domain;

public interface StreamingMusicUrlParser {

    String extractId(String url);

    boolean check(String url);

    StreamingMusicType type();
}
