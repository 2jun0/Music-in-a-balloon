package com.musicinaballoon.notification.application;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.BalloonReactionFixture.balloonReactionBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;
import static com.musicinaballoon.fixture.ReactionNotificationFixture.reactionNotificationBuilder;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.then;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.notification.application.response.ReactionNotificationResponse;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.DataWithMediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@ExtendWith(MockitoExtension.class)
class ReactionNotificationFacadeTest {

    @InjectMocks
    ReactionNotificationFacade reactionNotificationFacade;

    @Mock
    ReactionNotificationService reactionNotificationService;

    @Mock
    SseEmitterService sseEmitterService;

    @Mock
    UserService userService;

    private static List<ReactionNotification> reactionNotifications() {
        User user = userBuilder().build();
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user).build();
        BalloonReaction balloonReaction = balloonReactionBuilder(balloon, user).build();
        return List.of(reactionNotificationBuilder(balloonReaction, user).build(),
                reactionNotificationBuilder(balloonReaction, user).build());
    }

    private static String toPublishedSse(SseEventBuilder sseEventBuilder) {
        Set<DataWithMediaType> properties = sseEventBuilder.build();
        return properties.stream()
                .map(DataWithMediaType::getData)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    @Test
    @DisplayName("subscribe 는 빈 lastEventId 를 받을 수 있다.")
    void subscribe_InputsEmptyLastEventId() {
        // given
        SseEmitter mockEmitter = mock(SseEmitter.class);
        given(sseEmitterService.createSseEmitter(anyLong())).willReturn(mockEmitter);

        // when & then
        reactionNotificationFacade.subscribe(1L, "");
    }

    @Test
    @DisplayName("subscribe 는 lastEventId 를 밭으면 지난 리엑션 알림 이벤트들을 전송한다.")
    void subscribe_InputsLastEventId_SendLostReactionNotificationEvents() throws IOException {
        // given
        SseEmitter mockEmitter = mock(SseEmitter.class);
        List<ReactionNotification> reactionNotifications = reactionNotifications();

        given(sseEmitterService.createSseEmitter(anyLong())).willReturn(mockEmitter);
        given(reactionNotificationService.getLostNotifications(anyLong(), any(ZonedDateTime.class))).willReturn(
                reactionNotifications);

        // when
        reactionNotificationFacade.subscribe(1L, "");

        // then
        ArgumentCaptor<SseEventBuilder> argumentCaptor = ArgumentCaptor.forClass(SseEventBuilder.class);
        then(mockEmitter).should().send(argumentCaptor.capture());
        List<SseEventBuilder> captured = argumentCaptor.getAllValues();

        // verify the content of the SSE
        List<String> publishedSeeList =
                captured.subList(1, captured.size()).stream().map(ReactionNotificationFacadeTest::toPublishedSse)
                        .toList();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(reactionNotifications.size()).isEqualTo(publishedSeeList.size());

            for (int i = 0; i < reactionNotifications.size(); i++) {
                final ReactionNotification reactionNotification = reactionNotifications.get(i);
                final String publishedSse = publishedSeeList.get(i);

                String[] eventOutputs = publishedSse.split("\n");
                softly.assertThat(eventOutputs[0]).startsWith("id: " + reactionNotification.getCreatedAt().toString());
                softly.assertThat(eventOutputs[1]).isEqualTo("event: Reaction-Notification");
                softly.assertThat(eventOutputs[2]).isEqualTo("data:" + ReactionNotificationResponse.from(reactionNotification));
            }
        });
    }

    @Test
    void sendNotification() {
        // TODO
    }
}