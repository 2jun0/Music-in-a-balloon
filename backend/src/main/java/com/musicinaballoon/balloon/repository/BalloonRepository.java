package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.Balloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalloonRepository extends JpaRepository<Balloon, Long> {

}
