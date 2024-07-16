package com.musicinaballoon.balloon.repository;

import static com.musicinaballoon.balloon.domain.QBalloon.balloon;
import static com.musicinaballoon.balloon.domain.QBalloonPicked.balloonPicked;

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
    public List<Balloon> findNotPickedNotCreatedByUserOrderByCreatedAtDesc(User user, Pageable pageable) {
        return jpaQueryFactory.selectFrom(balloon)
                .leftJoin(balloonPicked)
                .on(balloon.eq(balloonPicked.balloon)
                        .and(balloonPicked.picker.eq(user)))
                .where(balloon.creator.ne(user)
                        .and(balloonPicked.isNull()))
                .orderBy(balloon.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Balloon> findPickedByPickerIdOrderByBalloonPickedCratedAtDesc(Long pickerId, Pageable pageable) {
        return jpaQueryFactory.selectFrom(balloon)
                .join(balloonPicked)
                .on(balloon.eq(balloonPicked.balloon)
                        .and(balloonPicked.picker.id.eq(pickerId)))
                .orderBy(balloonPicked.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
