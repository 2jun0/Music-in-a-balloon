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
import static org.mockito.Mockito.times;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.notification.application.response.ReactionNotificationResponse;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import java.io.IOException;
import java.time.Instant;
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
        User user = receiver();
        BalloonReaction balloonReaction = balloonReaction(user);

        List<ReactionNotification> reactionNotifications = List.of(reactionNotificationBuilder(balloonReaction, user).build(),
                reactionNotificationBuilder(balloonReaction, user).build());
        reactionNotifications.forEach(BaseEntity::prePersist);

        return reactionNotifications;
    }

    private static BalloonReaction balloonReaction(User user) {
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        Balloon balloon = youtubeMusicBalloonBuilder(youtubeMusic, user).build();
        BalloonReaction balloonReaction = balloonReactionBuilder(balloon, user).build();
        return balloonReaction;
    }

    private static User receiver() {
        User user = userBuilder().build();
        return user;
    }

    private static String toPublishedSse(SseEventBuilder sseEventBuilder) {
        Set<DataWithMediaType> properties = sseEventBuilder.build();
        return properties.stream()
                .map(DataWithMediaType::getData)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static void verifySseContent(SoftAssertions softly, SseEventBuilder sseEventBuilder,
            ReactionNotification reactionNotification) {
        String publishedSse = toPublishedSse(sseEventBuilder);
        String[] eventOutputs = publishedSse.split("\n");

        softly.assertThat(eventOutputs[0]).startsWith("id:" + reactionNotification.getCreatedAt().toString());
        softly.assertThat(eventOutputs[1]).isEqualTo("event:Reaction-Notification");
        softly.assertThat(eventOutputs[2]).isEqualTo("data:" + ReactionNotificationResponse.from(reactionNotification));
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
        reactionNotificationFacade.subscribe(1L, Instant.now().toString());

        // then
        ArgumentCaptor<SseEventBuilder> argumentCaptor = ArgumentCaptor.forClass(SseEventBuilder.class);
        then(mockEmitter).should(times(3)).send(argumentCaptor.capture());
        List<SseEventBuilder> captured = argumentCaptor.getAllValues();

        SoftAssertions.assertSoftly(softly -> {
            for (int i = 0; i < reactionNotifications.size(); i++) {
                ReactionNotification reactionNotification = reactionNotifications.get(i);
                SseEventBuilder sseEventBuilder = captured.get(i + 1);

                verifySseContent(softly, sseEventBuilder, reactionNotification);
            }
        });
    }

    @Test
    @DisplayName("sendNotification 은 SseEmitter 가 구독되었을때 알림 이벤트를 보낸다.")
    void sendNotification_SseEmitterSubscribed_SendNotificationEvent() throws IOException {
        // given
        User receiver = receiver();
        BalloonReaction balloonReaction = balloonReaction(receiver);
        ReactionNotification reactionNotification = reactionNotificationBuilder(balloonReaction, receiver).build();

        SseEmitter mockedEmitter = mock(SseEmitter.class);
        given(sseEmitterService.existsEmitter(anyLong())).willReturn(true);
        given(sseEmitterService.getSseEmitter(anyLong())).willReturn(mockedEmitter);
        given(reactionNotificationService.createReactionNotification(any(User.class), any(BalloonReaction.class))).willReturn(
                reactionNotification);

        // when
        reactionNotificationFacade.sendNotification(receiver, balloonReaction);

        // then
        ArgumentCaptor<SseEventBuilder> argumentCaptor = ArgumentCaptor.forClass(SseEventBuilder.class);
        then(mockedEmitter).should().send(argumentCaptor.capture());

        SoftAssertions.assertSoftly(softly -> {
            verifySseContent(softly, argumentCaptor.getValue(), reactionNotification);
        });
    }

    @Test
    @DisplayName("sendNotification 은 SseEmitter 가 구독되지 않았을 때 알림 이벤트를 보내지 않는다.")
    void sendNotification_SseEmitterNotSubscribed_SendNotificationEvent() {
        // given
        User receiver = receiver();
        BalloonReaction balloonReaction = balloonReaction(receiver);
        ReactionNotification reactionNotification = reactionNotificationBuilder(balloonReaction, receiver).build();

        given(sseEmitterService.existsEmitter(anyLong())).willReturn(false);
        given(reactionNotificationService.createReactionNotification(any(User.class), any(BalloonReaction.class))).willReturn(
                reactionNotification);

        // when & then
        reactionNotificationFacade.sendNotification(receiver, balloonReaction);
    }
}