package com.musicinaballoon.user.application;

import static com.musicinaballoon.fixture.UserFixture.DEFAULT_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @DisplayName("유저를 생성한다")
    @Test
    void createUser() {
        // given
        given(userRepository.save(any(User.class))).will(returnsFirstArg());

        // when
        User created = userService.createUser(DEFAULT_USERNAME);

        // then
        assertThat(created.getName()).isEqualTo(DEFAULT_USERNAME);
    }

    @DisplayName("유저를 조회한다")
    @Test
    void getUser() {
        // given
        User user = User.builder()
                .name(DEFAULT_USERNAME)
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when
        User gotten = userService.getUser(1L);

        // then
        assertThat(gotten).isEqualTo(user);
    }

    @DisplayName("없는 유저를 조회할시 BadRequest 예외를 던진다")
    @Test
    void getUser_NotExisted_ThrowsBadRequestException() {
        // given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getUser(1L)).isInstanceOf(BadRequestException.class);
    }
}