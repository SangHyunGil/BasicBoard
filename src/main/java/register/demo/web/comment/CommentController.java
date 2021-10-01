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
        log.info("comment : ", id);
        return commentService.findComments(id);
    }

    @PostMapping("/main/board/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestBody CommentForm commentForm, @Login LoginForm loginForm) {
        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        commentService.reply(id, new Comment(student.get().getName(), commentForm.getContent(), LocalDateTime.now()));
        return "true";
    }
}
