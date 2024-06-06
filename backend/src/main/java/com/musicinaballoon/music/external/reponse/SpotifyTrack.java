package com.musicinaballoon.music.external.reponse;

import se.michaelthelin.spotify.model_objects.specification.Track;

public record SpotifyTrack(
        String trackId,
        String name,
        SpotifyAlbum album
) {

    public static SpotifyTrack from(Track track) {
        return new SpotifyTrack(track.getId(), track.getName(), SpotifyAlbum.from(track.getAlbum()));
    }
}
