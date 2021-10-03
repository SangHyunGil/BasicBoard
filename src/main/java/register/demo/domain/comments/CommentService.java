package register.demo.domain.comments;

import java.util.List;

public interface CommentService {
    void addComment(Long boardId, Comment comment);

    List<Comment> findComments(Long boardId);

    void deleteComment(Long boardId, Long commentId);

    void updateComment(Long commentId, String content);

    void replyComment(Long parentId, Comment childComment);
}
