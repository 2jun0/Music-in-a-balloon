package com.musicinaballoon.music.external;

import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.ServiceUnavailableException;
import com.musicinaballoon.music.external.response.SpotifyTrack;
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

    public SpotifyTrack getTrack(String trackId) {
        return SpotifyTrack.from(getTrackFromApi(trackId));
    }

    private Track getTrackFromApi(String trackId) {
        try {
            return spotifyApi.getTrack(trackId)
                    .build()
                    .execute();
        } catch (NotFoundException exception) {
            throw new com.musicinaballoon.common.exception.BadRequestException(ErrorCode.SPOTIFY_MUSIC_NOT_FOUND);
        } catch (IOException | ParseException | SpotifyWebApiException exception) {
            throw new ServiceUnavailableException(ErrorCode.SPOTIFY_API_SERVICE_UNAVAILABLE, exception);
        }
    }
}
