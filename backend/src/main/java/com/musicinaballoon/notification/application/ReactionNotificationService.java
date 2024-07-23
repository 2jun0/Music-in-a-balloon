package com.musicinaballoon.notification.application;

import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.notification.domain.ReactionNotification;
import com.musicinaballoon.notification.repository.ReactionNotificationRepository;
import com.musicinaballoon.user.domain.User;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionNotificationService {

    private final ReactionNotificationRepository reactionNotificationRepository;

    public ReactionNotification createReactionNotification(User receiver, BalloonReaction balloonReaction) {
        return reactionNotificationRepository.save(ReactionNotification.builder()
                .receiver(receiver)
                .balloonReaction(balloonReaction)
                .build());
    }

    public List<ReactionNotification> getLostNotifications(Long receiverId, ZonedDateTime lastReactionNotificationCreatedAt) {
        return reactionNotificationRepository.findByReceiverIdAndCreatedAtGreaterThanOrderByCreatedAtAsc(receiverId,
                lastReactionNotificationCreatedAt);
    }
}
