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
import register.demo.web.login.LoginForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final StudentService studentService;

    @GetMapping("/main/board/comment")
    public List<Comment> getCommentList(@RequestParam Long boardId){
        log.info("loadComment : {}", boardId);
        return commentService.findComments(boardId);
    }

    @PostMapping("/main/board/comment")
    public String addComment(@RequestBody CommentAddForm commentAddForm, @Login LoginForm loginForm) {
        log.info("addComment : {}, {}", commentAddForm.getBoardId(), commentAddForm.getContent());
        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        commentService.addComment(commentAddForm.getBoardId(), new Comment(student.get(), commentAddForm.getContent(), LocalDateTime.now(), false));
        return "true";
    }

    @PostMapping("/main/board/comment/reply")
    public String replyComment(@RequestBody CommentReplyForm commentReplyForm, @Login LoginForm loginForm) {
        log.info("replyComment : {}, {}", commentReplyForm.getParentId(), commentReplyForm.getContent());
        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        commentService.replyComment(commentReplyForm.getParentId(), new Comment(student.get(), commentReplyForm.getContent(), LocalDateTime.now(), false));
        return "true";
    }

    @DeleteMapping("/main/board/comment")
    public String deleteComment(@RequestBody CommentDeleteForm commentDeleteForm) {
        log.info("deleteComment : {}", commentDeleteForm.getCommentId());
        commentService.deleteComment(commentDeleteForm.getBoardId(), commentDeleteForm.getCommentId());
        return "true";
    }

    @PatchMapping("/main/board/comment")
    public String updateComment( @RequestBody CommentUpdateForm commentUpdateForm) {
        log.info("updateComment : {}, {}", commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        commentService.updateComment(commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        return "true";
    }
}
