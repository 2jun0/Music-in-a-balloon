package com.musicinabottle.music.streaming.spotify;

import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.StreamingMusicUrlParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class SpotifyMusicUrlParser implements StreamingMusicUrlParser {

    private static final Pattern SPOTIFY_URL_PATTERN = Pattern.compile("(?<=/track/)[^?&#]*");

    @Override
    public String extractId(String url) {
        Matcher matcher = SPOTIFY_URL_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new InvalidSpotifyMusicException();
        }
        return matcher.group();
    }

    @Override
    public boolean check(String url) {
        Matcher matcher = SPOTIFY_URL_PATTERN.matcher(url);
        return matcher.find();
    }

    @Override
    public StreamingMusicType type() {
        return StreamingMusicType.SPOTIFY_MUSIC;
    }
}
