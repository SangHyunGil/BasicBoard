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
import register.demo.web.comment.dto.cCommentDto;
import register.demo.web.comment.dto.pCommentDto;
import register.demo.web.comment.form.CommentAddForm;
import register.demo.web.comment.form.CommentDeleteForm;
import register.demo.web.comment.form.CommentReplyForm;
import register.demo.web.comment.form.CommentUpdateForm;
import register.demo.web.login.LoginForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final StudentService studentService;

    @GetMapping("/main/board/comment")
    public List<pCommentDto> getCommentList(@RequestParam Long boardId){
        log.info("loadComment : {}", boardId);
        List<Comment> comments = commentService.findComments(boardId);
        List<pCommentDto> collect = comments.stream()
                .map(pc -> new pCommentDto(pc.getId(), pc.getWriter().getNickname(), pc.getContent(), pc.getWriteTime(), pc.getIsDelete(), getcCommentDtoList(pc)))
                .collect(Collectors.toList());

        for (pCommentDto pCommentDto : collect) {
            log.info("pCommentDto = {}", pCommentDto);
        }
        return collect;
    }

    private List<cCommentDto> getcCommentDtoList(Comment pc) {
        return pc.getChildren().stream()
                .map(cc -> new cCommentDto(cc.getId(), cc.getWriter().getNickname(), cc.getContent(), cc.getWriteTime(), cc.getIsDelete()))
                .collect(Collectors.toList());
    }

    @PostMapping("/main/board/comment")
    public String addComment(@RequestBody CommentAddForm commentAddForm, @Login LoginForm loginForm) {
        log.info("addComment : {}, {}", commentAddForm.getBoardId(), commentAddForm.getContent());
        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        commentService.addComment(commentAddForm.getBoardId(), new Comment(student.get(), commentAddForm.getContent(), LocalDateTime.now(), false));
        return "addComplete";
    }

    @PostMapping("/main/board/comment/reply")
    public String replyComment(@RequestBody CommentReplyForm commentReplyForm, @Login LoginForm loginForm) {
        log.info("replyComment : {}, {}", commentReplyForm.getParentId(), commentReplyForm.getContent());
        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        commentService.replyComment(commentReplyForm.getParentId(), new Comment(student.get(), commentReplyForm.getContent(), LocalDateTime.now(), false));
        return "replyComplete";
    }

    @DeleteMapping("/main/board/comment")
    public String deleteComment(@RequestBody CommentDeleteForm commentDeleteForm) {
        log.info("deleteComment : {}, {}", commentDeleteForm.getCommentId());
        commentService.deleteComment(commentDeleteForm.getBoardId(), commentDeleteForm.getCommentId());
        return "deleteComplete";
    }

    @PatchMapping("/main/board/comment")
    public String updateComment(@RequestBody CommentUpdateForm commentUpdateForm) {
        log.info("updateComment : {}, {}", commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        commentService.updateComment(commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        return "updateComplete";
    }
}
