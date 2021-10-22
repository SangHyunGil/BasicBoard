package register.demo.domain.like;

import java.util.Optional;

public interface PostLikeCustomRepository {
    Optional<PostLike> exist(Long studentId, Long boardId);
    long findPostLikeNum(Long boardId);
}
