package register.demo.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import register.demo.like.domain.PostLike;

import javax.persistence.EntityManager;

import java.util.Optional;

import static register.demo.like.QPostLike.postLike;

public class PostLikeCustomRepositoryImpl implements PostLikeCustomRepository{

    JPAQueryFactory jpaQueryFactory;

    public PostLikeCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Optional<PostLike> exist(Long studentId, Long boardId) {
        PostLike pLike = jpaQueryFactory
                .selectFrom(postLike)
                .where(postLike.student.id.eq(studentId),
                        postLike.board.id.eq(boardId))
                .fetchFirst();

        return Optional.ofNullable(pLike);
    }

    public long findPostLikeNum(Long boardId) {
        return jpaQueryFactory
                .selectFrom(postLike)
                .where(postLike.board.id.eq(boardId))
                .fetchCount();
    }
}
