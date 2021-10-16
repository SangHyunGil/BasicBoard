package register.demo.domain.comments;

import register.demo.web.comment.form.CommentAddForm;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentAddForm commentAddForm);

    List<Comment> findComments(Long boardId);

    Boolean deleteComment(Long commentId);

    Boolean updateComment(Long commentId, String content);
}
