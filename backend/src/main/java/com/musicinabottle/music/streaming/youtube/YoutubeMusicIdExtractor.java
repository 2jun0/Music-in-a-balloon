package com.musicinabottle.music.streaming.youtube;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class YoutubeMusicIdExtractor {

    private static final Pattern YOUTUBE_URL_PATTERN = Pattern.compile("(?<=watch\\?v=)[^&#]*");

    //https://www.youtube.com/watch?v=PxC5kDvxoSs&ab_channel=%EC%B9%A8%EC%B0%A9%EB%A7%A8
    //    https://youtu.be/PxC5kDvxoSs?si=AItzwao6W848rgnC
    public String extractId(String url) throws InvalidYoutubeMusicUrlException {
        Matcher matcher = YOUTUBE_URL_PATTERN.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new InvalidYoutubeMusicUrlException();
    }
}
