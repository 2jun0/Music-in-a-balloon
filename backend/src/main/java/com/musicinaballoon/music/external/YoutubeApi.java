package com.musicinaballoon.music.external;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.ServiceUnavailableException;
import com.musicinaballoon.music.external.response.YoutubeVideo;
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

    public YoutubeVideo getVideo(String videoId) {
        return YoutubeVideo.from(getVideoFromApi(videoId));
    }

    private Video getVideoFromApi(String videoId) {
        try {
            List<Video> items = youtube.videos().list("snippet")
                    .setKey(apiKey)
                    .setId(videoId)
                    .execute()
                    .getItems();

            if (items.isEmpty()) {
                throw new BadRequestException(ErrorCode.INVALID_YOUTUBE_MUSIC_ID);
            }
            return items.getFirst();
        } catch (IOException e) {
            throw new ServiceUnavailableException(ErrorCode.YOUTUBE_API_SERVICE_UNAVAILABLE, e);
        }
    }
}
