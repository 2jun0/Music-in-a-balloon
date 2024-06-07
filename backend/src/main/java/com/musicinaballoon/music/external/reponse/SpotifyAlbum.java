package com.musicinaballoon.music.external.reponse;

import java.util.Arrays;
import java.util.List;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

public record SpotifyAlbum(List<SpotifyImage> images) {

    public static SpotifyAlbum from(AlbumSimplified album) {
        return new SpotifyAlbum(
                Arrays.stream(album.getImages()).map(SpotifyImage::from).toList());
    }
}
