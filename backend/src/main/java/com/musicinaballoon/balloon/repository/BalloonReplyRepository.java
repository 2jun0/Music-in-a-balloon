package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.BalloonReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalloonReplyRepository extends JpaRepository<BalloonReply, Long> {

    boolean existsByBalloonIdAndReplierId(Long balloonId, Long replierId);
}
