package register.demo.domain.comments;

import java.util.List;

public interface CommentRepository {
    Long saveComment(Long id, Comment comment);

    Boolean deleteComment(Long boardId, Long commentId);

    Boolean updateComment(Long commentId, String content);

    Boolean replyComment(Long commentId, Comment comment);

    List<Comment> findAllComment(Long id);
}
