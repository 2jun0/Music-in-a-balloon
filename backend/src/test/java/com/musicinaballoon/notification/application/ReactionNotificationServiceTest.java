package com.musicinaballoon.notification.application;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.BalloonReactionFixture.balloonReactionBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;
import static com.musicinaballoon.fixture.ReactionNotificationFixture.reactionNotificationBuilder;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.notification.repository.ReactionNotificationRepository;
import com.musicinaballoon.user.domain.User;
import java.time.ZonedDateTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReactionNotificationServiceTest {

    @InjectMocks
    ReactionNotificationService reactionNotificationService;

    @Mock
    ReactionNotificationRepository reactionNotificationRepository;

    private static BalloonReaction balloonReaction(User receiver) {
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, receiver).build();
        BalloonReaction balloonReaction = balloonReactionBuilder(balloon, receiver).build();
        return balloonReaction;
    }

    @Test
    @DisplayName("createReactionNotification 은 유효한 입력을 받으면 ReactionNotification 을 반환한다.")
    void createReactionNotification_InputsValid_ReturnsReactionNotification() {
        // given
        User receiver = userBuilder().build();
        BalloonReaction balloonReaction = balloonReaction(receiver);
        given(reactionNotificationRepository.save(any(ReactionNotification.class))).will(returnsFirstArg());

        // when
        ReactionNotification created = reactionNotificationService.createReactionNotification(receiver,
                balloonReaction);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(created.getBalloonReaction()).isEqualTo(balloonReaction);
            softly.assertThat(created.getReceiver()).isEqualTo(receiver);
        });
    }

    @Test
    @DisplayName("getLostNotifications 은 유효한 입력을 받으면 ReactionNotification 들을 반환한다.")
    void getLostNotifications_InputsValid_ReturnsReactionNotifications() {
        // given
        User receiver = userBuilder().build();
        List<ReactionNotification> reactionNotifications = List.of(
                reactionNotificationBuilder(balloonReaction(receiver), receiver).build(),
                reactionNotificationBuilder(balloonReaction(receiver), receiver).build());
        given(reactionNotificationRepository.findByReceiverIdAndCreatedAtGreaterThanOrderByCreatedAtAsc(anyLong(), any(
                ZonedDateTime.class))).willReturn(reactionNotifications);

        // when
        List<ReactionNotification> gotten = reactionNotificationService.getLostNotifications(1L, ZonedDateTime.now());

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(gotten).isEqualTo(reactionNotifications);
        });
    }
}