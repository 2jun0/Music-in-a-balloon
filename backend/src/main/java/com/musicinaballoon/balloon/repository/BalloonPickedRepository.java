package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonPicked;
import com.musicinaballoon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalloonPickedRepository extends JpaRepository<BalloonPicked, Long> {

    boolean existsByBalloonAndPicker(Balloon balloon, User picker);
}
