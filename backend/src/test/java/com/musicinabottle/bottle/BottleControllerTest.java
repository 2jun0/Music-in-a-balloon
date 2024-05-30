package com.musicinabottle.bottle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicinabottle.bottle.request.CreateBottleRequest;
import com.musicinabottle.bottle.response.BottleResponse;
import com.musicinabottle.music.streaming.StreamingMusicType;
import com.musicinabottle.user.User;
import com.musicinabottle.user.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class BottleControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("병을 줍는다")
    void pickBottle() {

    }

    @Test
    @Transactional
    @DisplayName("유튜브 음악 URL로 병을 생성한다")
    void createBottleByYoutubeMusicUrl() throws Exception {
        var user = User.builder()
                .name("username").build();
        userRepository.save(user);

        var request = new CreateBottleRequest("https://music.youtube.com/watch?v=n7ePZLn9_lQ");
        var response = new BottleResponse(1L, "Super Shy", StreamingMusicType.YOUTUBE_MUSIC.name(), null);

        mockMvc.perform(post("/bottle")
                        .cookie(new Cookie("userId", user.getId().toString()))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.title").value(response.title()))
                .andExpect(jsonPath("$.uploadedStreamingMusicType").value(response.uploadedStreamingMusicType()))
                .andReturn();

        var saved = bottleRepository.findById(response.id()).orElseThrow();
        assert saved.getYoutubeMusic().getTitle().equals(response.title());
        assert saved.getUploadedStreamingMusicType().name().equals(response.uploadedStreamingMusicType());
    }

    @Test
    @Transactional
    @DisplayName("스포티파이 음악 URL로 병을 생성한다")
    void createBottleBySpotifyMusicUrl() throws Exception {
        var request = new CreateBottleRequest("https://open.spotify.com/track/5sdQOyqq2IDhvmx2lHOpwd");
        var response = new BottleResponse(1L, "Super Shy", StreamingMusicType.SPOTIFY_MUSIC.name(), null);

        mockMvc.perform(post("/bottle")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.title").value(response.title()))
                .andExpect(jsonPath("$.uploadedStreamingMusicType").value(response.uploadedStreamingMusicType()));

        var saved = bottleRepository.findById(response.id()).orElseThrow();
        assert saved.getSpotifyMusic().getTitle().equals(response.title());
        assert saved.getUploadedStreamingMusicType().name().equals(response.uploadedStreamingMusicType());
    }
}