package com.musicinabottle.music.streaming.youtube;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Videos;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YoutubeApi {
    @Value("${youtube.api_key}")
    private String apiKey;

    private final HttpTransport httpTransport = new NetHttpTransport();
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    private final YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, request -> {
    }).build();

    public Video getVideo(String videoId) throws IOException {
        Videos.List list = youtube.videos().list("snippet");
        list.setKey(apiKey);
        list.setId(videoId);
        VideoListResponse videoListResponse = list.execute();
        List<Video> items = videoListResponse.getItems();

        if (items.isEmpty()) {
            throw new InvalidYoutubeMusicException();
        }

        return items.getFirst();
    }
}
