package com.musicinaballoon.balloon.domain;

import com.musicinaballoon.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity(name = "balloon_reply")
@NoArgsConstructor
public class BalloonReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "balloon_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Balloon balloon;

    @JoinColumn(name = "replier_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User replier;

    @Column(name = "message", length = 255, nullable = false)
    private String message;

    @Builder
    public BalloonReply(@NonNull Balloon balloon, @NonNull User replier, @NonNull String message) {
        this.balloon = balloon;
        this.replier = replier;
        this.message = message;
    }
}
