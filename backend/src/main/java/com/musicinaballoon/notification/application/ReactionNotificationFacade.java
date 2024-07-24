package com.musicinaballoon.notification.application;

import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.notification.application.response.ReactionNotificationResponse;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
public class ReactionNotificationFacade {

    private final SseEmitterService sseEmitterService;
    private final ReactionNotificationService reactionNotificationService;
    private final UserService userService;

    private static String makeEventId(ReactionNotification notification) {
        return notification.getCreatedAt().toString();
    }

    private static ZonedDateTime parseLastReactionNotificationCreatedAt(String lastEventId) {
        return ZonedDateTime.parse(lastEventId);
    }

    private static void sendDummyEvent(SseEmitter sseEmitter) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id("0")
                    .name("Dummy")
                    .data("Dummy message"));
        } catch (IOException e) {
            sseEmitter.complete();
        }
    }

    private static void sendEvent(SseEmitter sseEmitter, ReactionNotification reactionNotification) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(makeEventId(reactionNotification))
                    .name("Reaction-Notification")
                    .data(ReactionNotificationResponse.from(reactionNotification)));
        } catch (IOException e) {
            sseEmitter.complete();
        }
    }

    public SseEmitter subscribe(Long userId, String lastEventId) {
        userService.validateUserExisted(userId);

        SseEmitter sseEmitter = sseEmitterService.createSseEmitter(userId);
        sendDummyEvent(sseEmitter);

        if (!lastEventId.isEmpty()) {
            ZonedDateTime lastReactionNotificationCreatedAt = parseLastReactionNotificationCreatedAt(lastEventId);

            List<ReactionNotification> lostNotifications = reactionNotificationService.getLostNotifications(userId,
                    lastReactionNotificationCreatedAt);

            for (ReactionNotification lostNotification : lostNotifications) {
                sendEvent(sseEmitter, lostNotification);
            }
        }

        return sseEmitter;
    }

    public void sendNotification(Long receiverId, BalloonReaction balloonReaction) {
        User receiver = userService.getUser(receiverId);
        ReactionNotification reactionNotification = reactionNotificationService.createReactionNotification(receiver,
                balloonReaction);

        if (sseEmitterService.existsEmitter(receiverId)) {
            SseEmitter sseEmitter = sseEmitterService.getSseEmitter(receiver.getId());
            sendEvent(sseEmitter, reactionNotification);
        }
    }
}
