package com.musicinaballoon.notification.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.notification.repository.EmitterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@ExtendWith(MockitoExtension.class)
class SseEmitterServiceTest {

    @InjectMocks
    SseEmitterService sseEmitterService;

    @Mock
    EmitterRepository emitterRepository;

    @Test
    @DisplayName("createSseEmitter 는 유효한 입력을 받으면 SseEmitter 를 반환한다.")
    void createSseEmitter_InputsValid_ReturnsSseEmitter() {
        // given
        given(emitterRepository.save(anyLong(), any(SseEmitter.class))).will(returnsSecondArg());

        // when
        SseEmitter sseEmitter = sseEmitterService.createSseEmitter(1L);

        // then
        assertThat(sseEmitter).isNotNull();
    }
}