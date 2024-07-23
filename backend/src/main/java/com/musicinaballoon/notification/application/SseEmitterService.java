package com.musicinaballoon.notification.application;

import com.musicinaballoon.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SseEmitterService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final EmitterRepository emitterRepository;

    public SseEmitter createSseEmitter(final Long receiverId) {
        SseEmitter emitter = emitterRepository.save(receiverId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteByReceiverId(receiverId));
        emitter.onTimeout(() -> emitterRepository.deleteByReceiverId(receiverId));

        return emitter;
    }

    public boolean existsEmitter(Long receiverId) {
        return emitterRepository.existsByReceiverId(receiverId);
    }

    public SseEmitter getSseEmitter(Long receiverId) {
        return emitterRepository.findByReceiverId(receiverId);
    }
}
