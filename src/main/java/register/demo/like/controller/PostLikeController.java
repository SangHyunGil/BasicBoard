package register.demo.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import register.demo.like.service.PostLikeService;
import register.demo.student.domain.Student;
import register.demo.student.service.StudentService;
import register.demo.common.annotation.login.Login;
import register.demo.login.LoginForm;
import register.demo.like.controller.dto.PostLikeDto;
import register.demo.like.controller.dto.PostLikeResponseDto;

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
