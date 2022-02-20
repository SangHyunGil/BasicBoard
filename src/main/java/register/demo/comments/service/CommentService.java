package register.demo.comments.service;

import register.demo.comments.domain.Comment;
import register.demo.comments.controller.dto.CommentAddDto;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentAddDto commentAddDto);

    List<Comment> findComments(Long boardId);

    Boolean deleteComment(Long commentId);

    Boolean deleteAll(Long boardId);

    Boolean updateComment(Long commentId, String content);
}
