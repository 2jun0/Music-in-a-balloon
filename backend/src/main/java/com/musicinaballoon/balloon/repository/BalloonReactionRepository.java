package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.BalloonReaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalloonReactionRepository extends JpaRepository<BalloonReaction, Long> {

    Optional<BalloonReaction> findByBalloonIdAndUserId(Long balloonId, Long userId);

    void deleteByBalloonIdAndUserId(Long balloonId, Long userId);

    boolean existsByBalloonIdAndUserId(Long balloonId, Long userId);
}
