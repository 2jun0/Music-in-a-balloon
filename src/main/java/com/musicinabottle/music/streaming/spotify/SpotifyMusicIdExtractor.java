package com.musicinabottle.music.streaming.spotify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyMusicIdExtractor {

    private static final Pattern SPOTIFY_URL_PATTERN = Pattern.compile("(?<=/track/)[^?&#]*");

    public String extractId(String url) {
        Matcher matcher = SPOTIFY_URL_PATTERN.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new InvalidSpotifyMusicException();
    }
}
