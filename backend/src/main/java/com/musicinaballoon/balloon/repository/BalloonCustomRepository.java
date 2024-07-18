package com.musicinaballoon.balloon.repository;

import com.musicinaballoon.balloon.domain.Balloon;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BalloonCustomRepository {

    List<Balloon> findNotPickedByPickerIdOrderByCreatedAtDesc(Long pickerId, Pageable pageable);

    List<Balloon> findPickedByPickerIdOrderByBalloonPickedCratedAtDesc(Long pickerId, Pageable pageable);
}
