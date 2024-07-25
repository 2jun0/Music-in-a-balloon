package com.musicinaballoon.notification.presentation;

import com.musicinaballoon.auth.presentation.UserId;
import com.musicinaballoon.notification.application.ReactionNotificationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class ReactionNotificationController {

    private final ReactionNotificationFacade reactionNotificationFacade;

    @GetMapping(value = "/notification/reaction/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@UserId Long userId,
            @RequestHeader(value = "Last-Event-ID", required = false,
                    defaultValue = "") String lastEventId) {
        SseEmitter emitter = reactionNotificationFacade.subscribe(userId, lastEventId);
        return ResponseEntity.ok(emitter);
    }
}
