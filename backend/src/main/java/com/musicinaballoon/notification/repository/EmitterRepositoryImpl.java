package com.musicinaballoon.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(Long receiverId, SseEmitter sseEmitter) {
        emitters.put(receiverId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public boolean existsByReceiverId(Long receiverId) {
        return emitters.containsKey(receiverId);
    }

    @Override
    public SseEmitter findByReceiverId(Long receiverId) {
        return emitters.get(receiverId);
    }

    @Override
    public void deleteByReceiverId(Long receiverId) {
        emitters.remove(receiverId);
    }
}
