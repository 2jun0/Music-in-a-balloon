package com.musicinaballoon.notification.domain;

import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity(name = "notify")
@NoArgsConstructor
public class ReactionNotification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "balloon_reaction", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BalloonReaction balloonReaction;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @Builder
    public ReactionNotification(@NonNull BalloonReaction balloonReaction, @NonNull User receiver) {
        this.balloonReaction = balloonReaction;
        this.receiver = receiver;
    }
}
