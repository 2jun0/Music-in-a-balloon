package com.musicinaballoon.balloon.repository;

import static com.musicinaballoon.balloon.domain.QBalloon.balloon;
import static com.musicinaballoon.balloon.domain.QBalloonReply.balloonReply;

import com.musicinaballoon.balloon.domain.Balloon;
import com.musicinaballoon.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalloonCustomRepositoryImpl implements BalloonCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Balloon> findNotRepliedOrderByCreatedAtDesc(User replier, Pageable pageable) {
        return jpaQueryFactory.selectFrom(balloon)
                .leftJoin(balloonReply)
                .on(balloon.eq(balloonReply.balloon).and(balloonReply.replier.eq(replier)))
                .where(balloonReply.isNull())
                .orderBy(balloon.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
