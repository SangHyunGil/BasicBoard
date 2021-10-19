package register.demo.web.board;

import javassist.compiler.ast.Keyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
    public String showBoard(@Login LoginForm loginForm, @RequestParam(defaultValue = "") String keyword, Model model, @PageableDefault Pageable pageable) {
        log.info("searchForm : {}, {}, {}", keyword, pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("keyword", keyword);
        model.addAttribute("student", studentService.findStudent(loginForm.getEmail()).get());
        if (StringUtils.hasText(keyword)) {
            model.addAttribute("boards", boardService.findBoard(keyword));
        } else {
            model.addAttribute("boards", boardService.findBoards(Sort.by(Sort.Direction.DESC, "writeTime")));
        }
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
        Board board = new Board(boardForm.getTitle(), student.get(), boardForm.getContent(), LocalDateTime.now(), false, 0);
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
        boardService.updateHit(postId);
        model.addAttribute("board", board);

        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        model.addAttribute("student", student.get());
        return "post";
    }
}
