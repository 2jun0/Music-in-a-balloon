package com.musicinaballoon.music.application;

import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.external.SpotifyApi;
import com.musicinaballoon.music.repository.SpotifyMusicRepository;
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
        return spotifyMusicRepository.save(spotifyMusic);
    }

    private Optional<String> getAlbumUrl(Track track) {
        Image[] images = track.getAlbum().getImages();
        if (images.length == 0) {
            return Optional.empty();
        }
        return Optional.of(images[0].getUrl());
    }
}
