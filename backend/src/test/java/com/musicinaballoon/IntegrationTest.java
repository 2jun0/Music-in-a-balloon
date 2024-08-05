package com.musicinaballoon;

import static com.musicinaballoon.fixture.UserFixture.userBuilder;

import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.user.repository.UserRepository;
import com.musicinaballoon.wave.config.WaveDataLoader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

@Profile(value = "test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    protected User defaultUser;
    @Autowired
    protected UserRepository userRepository;
    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private WaveDataLoader waveDataLoader;

    @BeforeEach
    void setUp() {
        setPort();
        setAuthentication();
        waveDataLoader.run();
    }

    @AfterEach
    void reset() {
        clearDatabase();
    }

    void setPort() {
        RestAssured.port = port;
    }

    void clearDatabase() {
        databaseCleaner.clear();
    }

    void setAuthentication() {
        defaultUser = userBuilder().name("username").build();
        userRepository.save(defaultUser);
    }
}
