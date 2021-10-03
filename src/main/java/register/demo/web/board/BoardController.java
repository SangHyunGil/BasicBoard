package register.demo.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.login.LoginService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.annotation.login.Login;
import register.demo.web.login.LoginForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/main/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final StudentService studentService;

    @GetMapping
    public String showBoard(Model model) {
        model.addAttribute("boards", boardService.findBoards());

        return "board";
    }

    @GetMapping("/post")
    public String showPost(@ModelAttribute BoardForm boardForm) {
        return "post";
    }

    @PostMapping("/post")
    public String doPost(@Login LoginForm loginForm, @Validated @ModelAttribute BoardForm boardForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {]", bindingResult.getFieldError());
            return "post";
        }

        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        Board board = new Board(boardForm.getTitle(), student.get().getNickname(), boardForm.getContent(), LocalDateTime.now());
        boardService.post(board);
        return "redirect:/main/board";
    }

    @GetMapping("/{postId}")
    public String showPosting(@Login LoginForm loginForm, @PathVariable Long postId, Model model) {
        log.info("loging");
        Board board = boardService.findBoard(postId);
        model.addAttribute("board", board);

        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        model.addAttribute("student", student.get());
        return "posting";
    }
}
