package com.musicinaballoon.music.streaming.youtube;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YoutubeApi {
    private final HttpTransport httpTransport = new NetHttpTransport();
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private final YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, request -> {
    }).build();
    @Value("${youtube.api_key}")
    private String apiKey;

    public Video getVideo(String videoId) {
        try {
            List<Video> items = youtube.videos().list("snippet")
                    .setKey(apiKey)
                    .setId(videoId)
                    .execute()
                    .getItems();

            if (items.isEmpty()) {
                throw new InvalidYoutubeMusicException();
            }
            return items.getFirst();
        } catch (IOException e) {
            throw new YoutubeApiException();
        }
    }
}
