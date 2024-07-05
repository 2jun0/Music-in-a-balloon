package com.musicinaballoon.balloon.domain;

import com.musicinaballoon.common.domain.BaseEntity;
import com.musicinaballoon.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.Setter;

@Getter
@Entity(name = "balloon_picked")
@NoArgsConstructor
public class BalloonPicked extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "balloon_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Balloon balloon;

    @JoinColumn(name = "picker_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User picker;

    @Enumerated(EnumType.STRING)
    @Column(name = "react_type")
    @Setter
    private BalloonReactType reactType;

    @Builder
    public BalloonPicked(@NonNull Balloon balloon, @NonNull User picker, BalloonReactType reactType) {
        this.balloon = balloon;
        this.picker = picker;
        this.reactType = reactType;
    }
}
