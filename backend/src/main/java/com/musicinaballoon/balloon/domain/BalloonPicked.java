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
@Entity(name = "balloon_picked")
@NoArgsConstructor
public class BalloonPicked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "balloon_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Balloon balloon;

    @JoinColumn(name = "picker_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User picker;

    @Column(name = "reply_message", length = 255)
    private String replyMessage;

    @Builder
    public BalloonPicked(@NonNull Balloon balloon, @NonNull User picker, String replyMessage) {
        this.balloon = balloon;
        this.picker = picker;
        this.replyMessage = replyMessage;
    }
}
