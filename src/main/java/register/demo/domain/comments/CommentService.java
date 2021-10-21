package register.demo.domain.comments;

import register.demo.web.comment.dto.CommentAddDto;
import register.demo.web.comment.form.CommentAddForm;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentAddDto commentAddDto);

    List<Comment> findComments(Long boardId);

    Boolean deleteComment(Long commentId);

    Boolean deleteAll(Long boardId);

    Boolean updateComment(Long commentId, String content);
}
