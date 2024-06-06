package com.musicinaballoon.music.application;

import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.external.SpotifyApi;
import com.musicinaballoon.music.external.reponse.SpotifyAlbum;
import com.musicinaballoon.music.external.reponse.SpotifyTrack;
import com.musicinaballoon.music.repository.SpotifyMusicRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        SpotifyTrack track = spotifyApi.getTrack(spotifyId);
        SpotifyMusic spotifyMusic = SpotifyMusic.builder()
                .spotifyId(track.trackId())
                .title(track.name())
                .albumImageUrl(getAlbumImageUrl(track.album()).orElse(null))
                .build();
        return spotifyMusicRepository.save(spotifyMusic);
    }

    private Optional<String> getAlbumImageUrl(SpotifyAlbum album) {
        if (album.images().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(album.images().getFirst().url());
    }
}
