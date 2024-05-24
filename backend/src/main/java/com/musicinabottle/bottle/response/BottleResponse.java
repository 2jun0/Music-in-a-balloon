package com.musicinabottle.bottle.response;

import com.musicinabottle.bottle.Bottle;
import lombok.Builder;

@Builder
public record BottleResponse(String title, String uploadedStreamingMusicType, String albumImageUrl) {

    public static BottleResponse of(Bottle bottle) {
        return BottleResponse.builder()
                .title(bottle.getMusicTitle())
                .uploadedStreamingMusicType(bottle.getUploadedStreamingMusicType().name())
                .albumImageUrl(bottle.getAlbumImageUrl())
                .build();
    }
}
