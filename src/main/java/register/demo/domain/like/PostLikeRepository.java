package register.demo.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import register.demo.domain.board.Board;
import register.demo.domain.student.Student;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeCustomRepository {
}
