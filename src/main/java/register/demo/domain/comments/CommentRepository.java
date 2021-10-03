package register.demo.domain.comments;

import java.util.List;

public interface CommentRepository {
    void saveComment(Long id, Comment comment);

    void deleteComment(Long boardId, Long commentId);

    void updateComment(Long commentId, String content);

    void replyComment(Long commentId, Comment comment);

    List<Comment> findAllComment(Long id);
}
