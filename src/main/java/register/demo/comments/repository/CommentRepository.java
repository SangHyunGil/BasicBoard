package register.demo.comments.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import register.demo.comments.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"board", "writer"})
    @Query("select c from Comment c where c.board.id = :boardId")
    List<Comment> findAllComments(@Param("boardId") Long boardId);

    @EntityGraph(attributePaths = {"board", "parent", "writer", "children"})
    @Query("select c from Comment c where c.id= :commentId")
    Optional<Comment> findByIdWithParent(@Param("commentId") Long commentId);

    @EntityGraph(attributePaths = {"children"})
    void deleteByBoardId(Long boardId);
}
