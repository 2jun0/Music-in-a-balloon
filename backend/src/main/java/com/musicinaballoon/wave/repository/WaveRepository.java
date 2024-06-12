package com.musicinaballoon.wave.repository;

import com.musicinaballoon.wave.domain.Wave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaveRepository extends JpaRepository<Wave, Long> {
}
