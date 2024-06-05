package com.musicinaballoon.music.streaming.spotify;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

@RequiredArgsConstructor
@Service
public class SpotifyMusicService {
    private final SpotifyMusicRepository spotifyMusicRepository;
    private final SpotifyApi spotifyApi;

    public SpotifyMusic getSpotifyMusic(String spotifyId) {
        return spotifyMusicRepository.findBySpotifyId(spotifyId)
                .orElseGet(() -> createNewSpotifyMusic(spotifyId));
    }

    private SpotifyMusic createNewSpotifyMusic(String spotifyId) {
        Track track = spotifyApi.getTrack(spotifyId);
        SpotifyMusic spotifyMusic = SpotifyMusic.builder()
                .spotifyId(spotifyId)
                .title(track.getName())
                .albumImageUrl(getAlbumUrl(track).orElse(null))
                .build();
        spotifyMusicRepository.save(spotifyMusic);
        return spotifyMusic;
    }

    private Optional<String> getAlbumUrl(Track track) {
        Image[] images = track.getAlbum().getImages();
        if (images.length == 0) {
            return Optional.empty();
        }
        return Optional.of(images[0].getUrl());
    }
}
