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
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.annotation.login.Login;
import register.demo.web.login.LoginForm;

import java.time.LocalDateTime;
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
        return "doPost";
    }

    @PostMapping("/post")
    public String doPost(@Login LoginForm loginForm, @Validated @ModelAttribute BoardForm boardForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            return "doPost";
        }

        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        Board board = new Board(boardForm.getTitle(), student.get(), boardForm.getContent(), LocalDateTime.now(), false);
        boardService.post(board);
        return "redirect:/main/board";
    }

    @GetMapping("/{postId}/update")
    public String showUpdatePosting(@PathVariable Long postId, Model model) {
        Board board = boardService.findBoard(postId);
        BoardForm boardForm = new BoardForm(board.getTitle(), board.getWriter().getNickname(), board.getContent());
        model.addAttribute("postId", postId);
        model.addAttribute("boardForm", boardForm);
        return "updatePost";
    }

    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @Validated @ModelAttribute BoardForm boardForm,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            model.addAttribute("postId", postId);
            return "updatePost";
        }

        boardService.update(postId, boardForm);
        return "redirect:/main/board";
    }

    @GetMapping("/{postId}/delete")
    public String doUpdating(@PathVariable Long postId) {
        boardService.delete(postId);
        return "redirect:/main/board";
    }

    @GetMapping("/{postId}")
    public String showPosting(@Login LoginForm loginForm, @PathVariable Long postId, Model model) {
        Board board = boardService.findBoard(postId);
        model.addAttribute("board", board);

        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        model.addAttribute("student", student.get());
        return "post";
    }
}
