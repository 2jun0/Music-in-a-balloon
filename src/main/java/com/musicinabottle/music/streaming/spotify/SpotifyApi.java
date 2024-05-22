package com.musicinabottle.music.streaming.spotify;

import com.neovisionaries.i18n.CountryCode;
import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.BadRequestException;
import se.michaelthelin.spotify.exceptions.detailed.NotFoundException;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class SpotifyApi {

    private final se.michaelthelin.spotify.SpotifyApi spotifyApi;

    public SpotifyApi(@Value("${spotify.client_id}") String clientId, @Value("${spotify.client_secret}") String clientSecret) {
        spotifyApi = se.michaelthelin.spotify.SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
    }

    public Track getTrack(String trackId) throws IOException, ParseException, SpotifyWebApiException {
        return getTrack(trackId, CountryCode.ES);
    }

    public Track getTrack(String trackId, CountryCode countryCode) throws IOException, ParseException, SpotifyWebApiException {
        try {
            return spotifyApi.getTrack(trackId)
                    .market(countryCode)
                    .build()
                    .execute();
        } catch (BadRequestException | NotFoundException exception) {
            throw new InvalidSpotifyMusicException();
        }
    }
}
