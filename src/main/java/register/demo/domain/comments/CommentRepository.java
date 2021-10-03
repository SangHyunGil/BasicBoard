package register.demo.domain.comments;

import java.util.List;

public interface CommentRepository {
    void saveComment(Long id, Comment comment);

    void deleteComment(Long boardId, Long commentId);

    void updateComment(Long boardId, Long commentId, String content);

    List<Comment> findAllComment(Long id);
}
