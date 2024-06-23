package com.musicinaballoon.music.domain;

import com.musicinaballoon.common.domain.BaseEntity;

public abstract class StreamingMusic extends BaseEntity {

    public abstract String getTitle();

    public abstract String getAlbumImageUrl();

    public abstract String getMusicUrl();
}
