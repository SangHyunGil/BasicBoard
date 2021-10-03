package register.demo.domain.comments;

import java.util.List;

public interface CommentService {
    void reply(Long id, Comment comment);

    List<Comment> findComments(Long id);

    void delete(Long boardId, Long commentId);

    void update(Long boardId, Long commentId, String content);
}
