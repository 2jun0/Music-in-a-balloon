package com.musicinaballoon.notification.application;

import static com.musicinaballoon.common.exception.ErrorCode.FAIL_TO_MAP_REACTION_NOTIFICATION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.musicinaballoon.common.exception.InternalException;
import com.musicinaballoon.notification.application.response.ReactionNotificationResponse;

public class NotificationMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String convertReactionNotificationResponseToString(ReactionNotificationResponse notificationResponse) {
        try {
            return objectMapper.writeValueAsString(notificationResponse);
        } catch (JsonProcessingException e) {
            throw new InternalException(FAIL_TO_MAP_REACTION_NOTIFICATION);
        }
    }
}
