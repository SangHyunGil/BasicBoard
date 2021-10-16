package register.demo.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.board join fetch c.writer where c.board.id = :boardId")
    List<Comment> findAllComments(@Param("boardId") Long boardId);

    @Query("select c from Comment c left join fetch c.parent where c.id= :commentId")
    Optional<Comment> findByIdWithParent(@Param("commentId") Long commentId);
}
