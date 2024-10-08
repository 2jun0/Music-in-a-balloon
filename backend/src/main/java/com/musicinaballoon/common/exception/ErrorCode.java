package com.musicinaballoon.common.exception;

public enum ErrorCode {
    // 400
    INVALID_REQUEST_ARGUMENT("The request argument is invalid."),
    INVALID_STREAMING_MUSIC_URL("The streaming music url is invalid"),
    INVALID_YOUTUBE_MUSIC_ID("The YouTube music id is invalid"),
    INVALID_SPOTIFY_MUSIC_ID("The Spotify track id is invalid"),
    INVALID_IP_ADDRESS("The ip is invalid"),
    INVALID_USER_ID("The user id is invalid"),
    ALREADY_PICKED_BALLOON("The balloon is already picked"),
    TOO_FAR_TO_PICK_BALLOON("Too far to pick a balloon"),
    NOT_PICKED_BALLOON("The balloon was not picked"),

    // 401
    USER_COOKIE_REQUIRED("The user cookie is required"),

    // 404
    USER_NOT_FOUND("The user does not exist"),
    BALLOON_NOT_FOUND("The balloon does not exist"),
    BALLOON_REACTION_NOT_FOUND("The balloon reaction does not exist"),
    YOUTUBE_MUSIC_NOT_FOUND("The YouTube music does not exist"),
    SPOTIFY_MUSIC_NOT_FOUND("The Spotify music does not exist"),

    // 500
    INTERNAL_SERVER_ERROR("Internal server has a problem"),
    FAIL_TO_MAP_REACTION_NOTIFICATION("Fail to map reaction notification"),

    // 503
    YOUTUBE_API_SERVICE_UNAVAILABLE("The YouTube API service is unavailable"),
    SPOTIFY_API_SERVICE_UNAVAILABLE("The Spotify API service is unavailable"),
    IP_GEOLOCATION_UNAVAILABLE("Ip geolocation is unavailable");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
