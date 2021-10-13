package register.demo.domain.comments;

import java.util.List;

public interface CommentService {
    Long addComment(Long boardId, Comment comment);

    List<Comment> findComments(Long boardId);

    Boolean deleteComment(Long boardId, Long commentId);

    Boolean updateComment(Long commentId, String content);

    Boolean replyComment(Long parentId, Comment childComment);
}
