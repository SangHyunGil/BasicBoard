package register.demo.web.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import register.demo.domain.comments.Comment;
import register.demo.domain.comments.CommentService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.annotation.login.Login;
import register.demo.web.comment.dto.CommentAddDto;
import register.demo.web.comment.dto.CommentDto;
import register.demo.web.comment.form.CommentAddForm;
import register.demo.web.comment.form.CommentDeleteForm;
import register.demo.web.comment.form.CommentUpdateForm;
import register.demo.web.login.LoginForm;

import java.util.*;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final StudentService studentService;

    @GetMapping("/main/board/{boardId}/comment")
    public List<CommentDto> getCommentList(@PathVariable Long boardId){
        log.info("getComment : {}", boardId);
        List<Comment> comments = commentService.findComments(boardId);
        List<CommentDto> result = getCommentDtos(comments);

        return result;
    }

    private List<CommentDto> getCommentDtos(List<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();
        Map<Long, CommentDto> map = new HashMap<>();
        comments.stream()
                .forEach(c -> {
                    CommentDto commentDto = new CommentDto(c.getId(), c.getWriter().getNickname(), getContent(c), c.getWriteTime(), c.getIsDeleted());
                    map.put(commentDto.getId(), commentDto);
                    if (c.getParent() != null) map.get(c.getParent().getId()).getChildren().add(commentDto);
                    else result.add(commentDto);
                });

        return result;
    }

    private String getContent(Comment c) {
        return c.getIsDeleted() ? "삭제된 댓글입니다." : c.getContent();
    }

    @PostMapping("/main/board/{boardId}/comment")
    public String addComment(@PathVariable Long boardId, @RequestBody CommentAddForm commentAddForm, @Login LoginForm loginForm) {
        log.info("addComment : {}, {}", boardId, commentAddForm.getContent());

        Student student = studentService.findStudent(loginForm.getEmail()).get();
        CommentAddDto commentAddDto = commentAddForm.createCommentAddDto(boardId, student);
        commentService.addComment(commentAddDto);
        return "addComplete";
    }

    @DeleteMapping("/main/board/{boardId}/comment")
    public String deleteComment(@PathVariable Long boardId, @RequestBody CommentDeleteForm commentDeleteForm) {
        log.info("deleteComment : {}", commentDeleteForm.getCommentId());

        commentService.deleteComment(commentDeleteForm.getCommentId());
        return "deleteComplete";
    }

    @PatchMapping("/main/board/{boardId}/comment")
    public String updateComment(@PathVariable Long boardId, @RequestBody CommentUpdateForm commentUpdateForm) {
        log.info("updateComment : {}, {}", commentUpdateForm.getCommentId(), commentUpdateForm.getContent());

        commentService.updateComment(commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        return "updateComplete";
    }
}
