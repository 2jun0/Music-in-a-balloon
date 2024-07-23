package com.musicinaballoon.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    SseEmitter save(Long receiverId, SseEmitter sseEmitter);

    boolean existsByReceiverId(Long receiverId);

    SseEmitter findByReceiverId(Long receiverId);

    void deleteByReceiverId(Long receiverId);
}
