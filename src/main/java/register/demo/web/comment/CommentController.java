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

    @GetMapping("/main/board/{id}/comment")
    public List<Comment> getCommentList(@PathVariable Long id){
        log.info("loadComment : {}", id);
        return commentService.findComments(id);
    }

    @PostMapping("/main/board/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestBody CommentAddForm commentAddForm, @Login LoginForm loginForm) {
        log.info("addComment : {}, {}", id, commentAddForm.getContent());
        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        commentService.reply(id, new Comment(student.get().getNickname(), commentAddForm.getContent(), LocalDateTime.now()));
        return "true";
    }

    @DeleteMapping("/main/board/{id}/comment")
    public String deleteComment(@PathVariable Long id, @RequestBody CommentDeleteForm commentDeleteForm) {
        log.info("deleteComment : {}", id, commentDeleteForm.getCommentId());
        commentService.delete(id, commentDeleteForm.getCommentId());
        return "true";
    }

    @PatchMapping("/main/board/{id}/comment")
    public String updateComment(@PathVariable Long id, @RequestBody CommentUpdateForm commentUpdateForm) {
        log.info("updateComment : {}, {}", id, commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        commentService.update(id, commentUpdateForm.getCommentId(), commentUpdateForm.getContent());
        return "true";
    }
}
