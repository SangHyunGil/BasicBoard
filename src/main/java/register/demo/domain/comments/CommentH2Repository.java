package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import register.demo.domain.board.Board;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentH2Repository implements CommentRepository{

    private final EntityManager em;

    public void saveComment(Long id, Comment comment) {
        Board board = em.find(Board.class, id);
        comment.setBoard(board);
        em.persist(comment);
    }

    public void deleteComment(Long boardId, Long commentId) {
        Comment comment = em.find(Comment.class, commentId);

        if (comment.getChildren().isEmpty()) {
            comment.setIsDelete(true);
        } else {
            comment.setContent("삭제된 댓글입니다.");
        }
    }

    public void updateComment(Long commentId, String content) {
        Comment comment = em.find(Comment.class, commentId);
        comment.setContent(content);
    }

    public void replyComment(Long parentId, Comment childComment) {
        Comment comment = em.find(Comment.class, parentId);
        comment.addChildComment(childComment);
        em.persist(childComment);
    }

    public List<Comment> findAllComment(Long id) {
        return em.createQuery("select c from Comment c where c.board.id =: id and c.isDelete =: isDelete", Comment.class)
                .setParameter("id", id)
                .setParameter("isDelete", false)
                .getResultList();
    }
}