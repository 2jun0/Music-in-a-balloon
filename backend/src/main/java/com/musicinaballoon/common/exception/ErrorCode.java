package com.musicinaballoon.common.exception;

public enum ErrorCode {
    // 400
    INVALID_STREAMING_MUSIC_URL("The streaming music url is invalid"),
    INVALID_YOUTUBE_MUSIC_ID("The YouTube music id is invalid"),
    INVALID_SPOTIFY_MUSIC_ID("The Spotify track id is invalid"),

    // 404
    USER_NOT_FOUND("The user does not exist"),
    BALLOON_NOT_FOUND("The balloon does not exist"),

    // 500
    INTERNAL_SERVER_ERROR("Internal server has a problem"),

    // 503
    YOUTUBE_API_SERVICE_UNAVAILABLE("The YouTube API service is unavailable"),
    SPOTIFY_API_SERVICE_UNAVAILABLE("The Spotify API service is unavailable"),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
