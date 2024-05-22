package com.musicinabottle.music.streaming.spotify;

import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

@RequiredArgsConstructor
@Service
public class SpotifyMusicService {
    private final SpotifyMusicRepository spotifyMusicRepository;
    private final SpotifyMusicIdExtractor musicIdExtractor;
    private final SpotifyApi spotifyApi;

    public SpotifyMusic getSpotifyMusic(String spotifyMusicUrl)
            throws IOException, ParseException, SpotifyWebApiException {
        String spotifyId = musicIdExtractor.extractId(spotifyMusicUrl);
        return spotifyMusicRepository.findBySpotifyId(spotifyId)
                .orElse(createNewSpotifyMusic(spotifyId));
    }

    private SpotifyMusic createNewSpotifyMusic(String spotifyId) throws IOException, ParseException, SpotifyWebApiException {
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
