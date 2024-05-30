package com.musicinabottle.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(@NonNull String username) {
        User user = User.builder()
                .name(username)
                .build();
        userRepository.save(user);
        return user;
    }

    public User getUser(@NonNull Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFound::new);
    }
}
