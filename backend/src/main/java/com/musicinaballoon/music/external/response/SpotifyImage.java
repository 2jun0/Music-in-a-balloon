package com.musicinaballoon.music.external.response;

import se.michaelthelin.spotify.model_objects.specification.Image;

public record SpotifyImage(String url) {

    public static SpotifyImage from(Image image) {
        return new SpotifyImage(image.getUrl());
    }
}
