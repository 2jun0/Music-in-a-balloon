package com.musicinaballoon.music.external.response;

import com.google.api.services.youtube.model.ThumbnailDetails;

public record YoutubeThumbnail(String standardUrl) {

    public static YoutubeThumbnail from(ThumbnailDetails thumbnail) {
        return new YoutubeThumbnail(thumbnail.getStandard().getUrl());
    }
}
