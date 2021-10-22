package register.demo.web.postlike;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import register.demo.domain.like.PostLikeService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.annotation.login.Login;
import register.demo.web.login.LoginForm;
import register.demo.web.postlike.dto.PostLikeDto;
import register.demo.web.postlike.dto.PostLikeResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main/board/")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final StudentService studentService;

    @GetMapping("{boardId}/postLike")
    public PostLikeResponseDto getPostLikeInfo(@Login LoginForm loginForm, @PathVariable Long boardId) {
        Student student = studentService.findStudent(loginForm.getEmail()).get();
        PostLikeDto postLikeDto = new PostLikeDto(student, boardId);
        PostLikeResponseDto postLikeInfo = postLikeService.getPostLikeInfo(postLikeDto);

        return postLikeInfo;
    }

    @PostMapping("{boardId}/postLike")
    public Boolean pushLikeButton(@Login LoginForm loginForm, @PathVariable Long boardId) {
        Student student = studentService.findStudent(loginForm.getEmail()).get();
        PostLikeDto postLikeDto = new PostLikeDto(student, boardId);

        return postLikeService.pushLikeButton(postLikeDto);
    }
}
