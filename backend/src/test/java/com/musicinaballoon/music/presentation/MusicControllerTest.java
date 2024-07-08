package com.musicinaballoon.music.presentation;

import com.musicinaballoon.IntegrationTest;
import com.musicinaballoon.music.repository.SpotifyMusicRepository;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;

abstract class MusicControllerTest extends IntegrationTest {

    @Autowired
    protected YoutubeMusicRepository youtubeMusicRepository;

    @Autowired
    protected SpotifyMusicRepository spotifyMusicRepository;
}
