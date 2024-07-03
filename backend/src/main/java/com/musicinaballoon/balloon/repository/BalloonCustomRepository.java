package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.user.domain.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BalloonCustomRepository {

    List<Balloon> findNotPickedOrderByCreatedAtDesc(User user, Pageable pageable);
}
