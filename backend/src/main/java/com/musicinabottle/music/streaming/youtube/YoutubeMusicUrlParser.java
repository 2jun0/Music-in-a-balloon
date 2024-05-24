package com.musicinabottle.music.streaming.youtube;

import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.music.streaming.StreamingMusicUrlParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class YoutubeMusicUrlParser implements StreamingMusicUrlParser {

    private static final Pattern YOUTUBE_URL_PATTERN = Pattern.compile("(?<=watch\\?v=)[^&#]*");

    @Override
    public String extractId(String url) {
        Matcher matcher = YOUTUBE_URL_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new InvalidYoutubeMusicException();
        }
        return matcher.group();
    }

    @Override
    public boolean check(String url) {
        Matcher matcher = YOUTUBE_URL_PATTERN.matcher(url);
        return matcher.find();
    }

    @Override
    public StreamingMusicType type() {
        return StreamingMusicType.YOUTUBE_MUSIC;
    }
}
