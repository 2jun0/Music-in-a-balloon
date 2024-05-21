package com.musicinabottle.music.streaming.youtube;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class YoutubeMusicIdExtractor {

    private static final Pattern YOUTUBE_URL_PATTERN = Pattern.compile("(?<=watch\\?v=)[^&#]*");

    public String extractId(String url) throws InvalidYoutubeMusicUrlException {
        Matcher matcher = YOUTUBE_URL_PATTERN.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new InvalidYoutubeMusicUrlException();
    }
}
