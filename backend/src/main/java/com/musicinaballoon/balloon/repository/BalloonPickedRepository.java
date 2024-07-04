package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.BalloonPicked;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalloonPickedRepository extends JpaRepository<BalloonPicked, Long> {

    Optional<BalloonPicked> findByBalloonIdAndPickerId(Long balloonId, Long pickerId);

    boolean existsByBalloonIdAndPickerId(Long balloonId, Long pickerId);
}
