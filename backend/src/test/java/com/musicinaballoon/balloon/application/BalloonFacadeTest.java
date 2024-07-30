package com.musicinaballoon.balloon.application;

import static com.musicinaballoon.fixture.BalloonFixture.youtubeMusicBalloonBuilder;
import static com.musicinaballoon.fixture.BalloonReactionFixture.balloonReactionBuilder;
import static com.musicinaballoon.fixture.MusicFixture.youtubeMusicBuilder;
import static com.musicinaballoon.fixture.UserFixture.userBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.musicinaballoon.balloon.application.request.ReactBalloonRequest;
import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.balloon.domain.BalloonReaction;
import com.musicinaballoon.balloon.domain.BalloonReactionType;
import com.musicinaballoon.music.application.MusicService;
import com.musicinaballoon.music.domain.YoutubeMusic;
import com.musicinaballoon.notification.application.ReactionNotificationFacade;
import com.musicinaballoon.user.application.UserService;
import com.musicinaballoon.user.domain.User;
import com.musicinaballoon.wave.application.WaveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalloonFacadeTest {

    @InjectMocks
    BalloonFacade balloonFacade;
    @Mock
    BalloonService balloonService;
    @Mock
    BalloonPickService balloonPickService;
    @Mock
    BalloonReactionService balloonReactionService;
    @Mock
    MusicService musicService;
    @Mock
    UserService userService;
    @Mock
    WaveService waveService;
    @Mock
    ReactionNotificationFacade reactionNotificationFacade;

    static Balloon balloon(User user) {
        YoutubeMusic youtubeMusic = youtubeMusicBuilder().build();
        return youtubeMusicBalloonBuilder(youtubeMusic, user).build();
    }

    @Test
    @DisplayName("reactBalloon 은 유효한 입력을 받으면 풍선 만든이에게 반응을 알린다.")
    void reactBalloon_InputsValid_NotifyReactionToBalloonCreator() {
        // given
        ReactBalloonRequest reactBalloonRequest = new ReactBalloonRequest(BalloonReactionType.BALLOON);
        User responder = userBuilder().id(1L).build();
        User balloonCreator = userBuilder().id(2L).build();
        Balloon balloon = balloon(balloonCreator);
        BalloonReaction balloonReaction = balloonReactionBuilder(balloon, responder).build();

        given(balloonReactionService.existsBalloonReaction(anyLong(), anyLong())).willReturn(false);
        given(balloonService.getBalloon(anyLong())).willReturn(balloon);
        given(userService.getUser(responder.getId())).willReturn(responder);
        given(balloonReactionService.createBalloonReaction(any(Balloon.class), any(User.class), any(BalloonReactionType.class)))
                .willReturn(balloonReaction);

        // when
        balloonFacade.reactBalloon(1L, reactBalloonRequest, responder.getId());

        // then
        then(reactionNotificationFacade).should().sendNotification(balloonCreator.getId(), balloonReaction);
    }
}