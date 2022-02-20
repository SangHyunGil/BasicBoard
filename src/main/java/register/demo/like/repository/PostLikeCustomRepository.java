package register.demo.like.repository;

import register.demo.like.domain.PostLike;

import java.util.Optional;

public interface PostLikeCustomRepository {
    Optional<PostLike> exist(Long studentId, Long boardId);
    long findPostLikeNum(Long boardId);
}
