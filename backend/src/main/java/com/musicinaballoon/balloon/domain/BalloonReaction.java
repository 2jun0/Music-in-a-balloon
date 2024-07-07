package com.musicinaballoon.balloon.domain;

import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Entity(name = "balloon_reaction")
@Table(indexes = {
        @Index(name = "index__balloon_id__user_id", columnList = "balloon_id, user_id", unique = true)
})
@NoArgsConstructor
public class BalloonReaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "balloon_id", updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Balloon balloon;

    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @Setter
    private BalloonReactionType type;

    @Builder
    public BalloonReaction(@NonNull Balloon balloon, @NonNull User user, @NonNull BalloonReactionType type) {
        this.balloon = balloon;
        this.user = user;
        this.type = type;
    }
}
