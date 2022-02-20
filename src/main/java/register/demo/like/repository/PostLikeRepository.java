package register.demo.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import register.demo.like.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeCustomRepository {
}
