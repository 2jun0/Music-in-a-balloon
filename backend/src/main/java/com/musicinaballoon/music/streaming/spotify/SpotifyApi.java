package com.musicinaballoon.music.streaming.spotify;

import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.ServiceUnavailableException;
import com.neovisionaries.i18n.CountryCode;
import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.NotFoundException;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Component
public class SpotifyApi {

    private final se.michaelthelin.spotify.SpotifyApi spotifyApi;

    public SpotifyApi(@Value("${spotify.client_id}") String clientId, @Value("${spotify.client_secret}") String clientSecret) {
        spotifyApi = se.michaelthelin.spotify.SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        initAuth();
    }

    private void initAuth() {
        spotifyApi.clientCredentials().build().executeAsync()
                .thenAccept(credentials -> spotifyApi.setAccessToken(credentials.getAccessToken()));
    }

    public Track getTrack(String trackId) {
        return getTrack(trackId, CountryCode.ES);
    }

    public Track getTrack(String trackId, CountryCode countryCode) {
        try {
            return spotifyApi.getTrack(trackId)
                    .market(countryCode)
                    .build()
                    .execute();
        } catch (NotFoundException exception) {
            throw new com.musicinaballoon.common.exception.BadRequestException(ErrorCode.INVALID_SPOTIFY_MUSIC_ID);
        } catch (IOException | ParseException | SpotifyWebApiException exception) {
            throw new ServiceUnavailableException(ErrorCode.SPOTIFY_API_SERVICE_UNAVAILABLE, exception);
        }
    }
}
