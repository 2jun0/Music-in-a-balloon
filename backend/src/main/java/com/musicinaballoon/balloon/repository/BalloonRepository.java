package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.Balloon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalloonRepository extends JpaRepository<Balloon, Long>, BalloonCustomRepository {
}
