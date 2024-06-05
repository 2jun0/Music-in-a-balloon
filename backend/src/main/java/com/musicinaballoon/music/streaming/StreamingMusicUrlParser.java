package com.musicinaballoon.music.streaming;

public interface StreamingMusicUrlParser {

    String extractId(String url);

    boolean check(String url);

    StreamingMusicType type();
}
