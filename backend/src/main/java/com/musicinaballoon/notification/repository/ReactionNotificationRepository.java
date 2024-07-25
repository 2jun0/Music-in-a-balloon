package com.musicinaballoon.notification.repository;

import com.musicinaballoon.notification.domain.ReactionNotification;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionNotificationRepository extends JpaRepository<ReactionNotification, Long> {
    List<ReactionNotification> findByReceiverIdAndCreatedAtGreaterThanOrderByCreatedAtAsc(Long receiverId,
            ZonedDateTime createdAt);
}
