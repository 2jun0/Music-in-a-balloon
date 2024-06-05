package com.musicinabottle.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String username) {
        User user = User.builder()
                .name(username)
                .build();
        userRepository.save(user);
        return user;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFound::new);
    }
}
