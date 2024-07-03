package com.musicinaballoon.balloon.repository;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.BalloonPickedFixture.balloonPickedBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.music.repository.YoutubeMusicRepository;
import com.musicinaballoon.support.RepositoryTest;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.user.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RepositoryTest
class BalloonRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BalloonRepository balloonRepository;

    @Autowired
    YoutubeMusicRepository youtubeMusicRepository;

    @Autowired
    BalloonPickedRepository balloonPickedRepository;

    @Test
    @DisplayName("findNotPickedOrderByCreatedAtDesc는 유효한 입력을 받으면 풍선리스트를 반환한다.")
    void findNotPickedOrderByCreatedAtDesc_InvalidInputs_ReturnsBalloons() {
        // given
        List<Balloon> balloons = balloons();
        User user = userRepository.save(userBuilder().build());
        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Balloon> founds = balloonRepository.findNotPickedOrderByCreatedAtDesc(user, pageable);

        // then
        assertThat(founds).isEqualTo(balloons);
    }

    @Test
    @DisplayName("findNotPickedOrderByCreatedAtDesc는 유저가 주운 풍선이 있으면 줍지 않은 풍선을 반환한다.")
    void findNotPickedOrderByCreatedAtDesc_HasPickedBalloonsByUser_ReturnNotPickedBalloons() {
        // given
        List<Balloon> balloons = balloons();
        User user = userRepository.save(userBuilder().build());

        List<Balloon> pickedBalloons = balloons.subList(0, 2);
        for (Balloon pickedBalloon : pickedBalloons) {
            balloonPickedRepository.save(balloonPickedBuilder(pickedBalloon, user).build());
        }

        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Balloon> founds = balloonRepository.findNotPickedOrderByCreatedAtDesc(user, pageable);

        // then
        List<Balloon> expectedBalloons = balloons.subList(2, balloons.size());
        assertThat(founds).isEqualTo(expectedBalloons);
    }

    List<Balloon> balloons() {
        User creator = userRepository.save(userBuilder().build());
        YoutubeMusic youtubeMusic = youtubeMusicRepository.save(youtubeMusicBuilder().build());

        List<Balloon> balloons = balloonRepository.saveAll(List.of(
                youtubeMusicBalloonBuilder(youtubeMusic, creator).build(),
                youtubeMusicBalloonBuilder(youtubeMusic, creator).build(),
                youtubeMusicBalloonBuilder(youtubeMusic, creator).build(),
                youtubeMusicBalloonBuilder(youtubeMusic, creator).build(),
                youtubeMusicBalloonBuilder(youtubeMusic, creator).build(),
                youtubeMusicBalloonBuilder(youtubeMusic, creator).build()
        ));
        balloons.sort(Comparator.comparing(BaseEntity::getCreatedAt).reversed());

        return balloons;
    }
}