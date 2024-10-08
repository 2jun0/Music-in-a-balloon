package com.musicinaballoon.fixture;

import com.musicinaballoon.music.domain.SpotifyMusic;
import com.musicinaballoon.music.domain.SpotifyMusic.SpotifyMusicBuilder;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.domain.YoutubeMusic.YoutubeMusicBuilder;

public final class MusicFixture {

    public static final String YOUTUBE_MUSIC_SUPER_SHY_ID = "n7ePZLn9_lQ";
    public static final String YOUTUBE_MUSIC_SUPER_SHY_TITLE = "Super Shy";
    public static final String YOUTUBE_MUSIC_SUPER_SHY_URL = "https://music.youtube.com/watch?v=n7ePZLn9_lQ";
    public static final String YOUTUBE_SUPER_SHY_URL = "https://youtu.be/n7ePZLn9_lQ";
    public static final String YOUTUBE_MUSIC_SUPER_SHY_THUMBNAIL_URL = "https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg";

    public static final String SPOTIFY_MUSIC_SUPER_SHY_ID = "5sdQOyqq2IDhvmx2lHOpwd";
    public static final String SPOTIFY_MUSIC_SUPER_SHY_TITLE = "Super Shy";
    public static final String SPOTIFY_MUSIC_SUPER_SHY_URL = "https://open.spotify.com/track/5sdQOyqq2IDhvmx2lHOpwd";
    public static final String SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL = "https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af";

    public static YoutubeMusicBuilder youtubeMusicBuilder() {
        return YoutubeMusic.builder()
                .youtubeId(YOUTUBE_MUSIC_SUPER_SHY_ID)
                .title(YOUTUBE_MUSIC_SUPER_SHY_TITLE)
                .thumbnailUrl(YOUTUBE_MUSIC_SUPER_SHY_THUMBNAIL_URL);
    }

    public static SpotifyMusicBuilder spotifyMusicBuilder() {
        return SpotifyMusic.builder()
                .spotifyId(SPOTIFY_MUSIC_SUPER_SHY_ID)
                .title(SPOTIFY_MUSIC_SUPER_SHY_TITLE)
                .albumImageUrl(SPOTIFY_MUSIC_SUPER_SHY_ALBUM_IMAGE_URL);
    }
}
