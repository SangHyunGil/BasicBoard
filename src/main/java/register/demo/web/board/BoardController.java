package register.demo.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.category.Category;
import register.demo.domain.category.CategoryType;
import register.demo.web.board.search.SearchCondition;
import register.demo.domain.file.AttachmentType;
import register.demo.domain.file.FileStore;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.annotation.login.Login;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.dto.BoardUpdateDto;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardForm;
import register.demo.web.board.form.BoardUpdateForm;
import register.demo.web.login.LoginForm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/main/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final StudentService studentService;
    private final FileStore fileStore;

    @GetMapping
    public String showBoard(@Login LoginForm loginForm, @PageableDefault(size = 4) Pageable pageable,
                            SearchCondition searchCondition, @RequestParam(required = false) CategoryType category, Model model) {
        model.addAttribute("category", category);
        model.addAttribute("student", studentService.findStudent(loginForm.getEmail()).get());
        model.addAttribute("hotPosts", boardService.findHotPosts());

        if (StringUtils.hasText(searchCondition.getContent())) {
            model.addAttribute("boards", boardService.findBoards(searchCondition, pageable));
        }
        else if (category != null) {
            model.addAttribute("boards", boardService.findBoards(category, pageable));
        }
        else {
            model.addAttribute("boards", boardService.findBoards(pageable));
        }
        return "board";
    }

    @GetMapping("/post")
    public String showPost(@ModelAttribute BoardAddForm boardAddForm) {
        return "doPost";
    }

    @PostMapping("/post")
    public String doPost(@Login LoginForm loginForm, @Validated @ModelAttribute BoardAddForm boardAddForm,
                         BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            return "doPost";
        }

        Student student = studentService.findStudent(loginForm.getEmail()).get();
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(student);
        Board post = boardService.post(boardPostDto);
        return "redirect:/main/board/"+post.getId();
    }

    @GetMapping("/{postId}/update")
    public String showUpdatePosting(@PathVariable Long postId, Model model) {
        Board board = boardService.findBoard(postId);
        BoardUpdateForm boardUpdateForm = new BoardUpdateForm(board.getTitle(), board.getWriter().getNickname(), board.getContent());
        model.addAttribute("postId", postId);
        model.addAttribute("boardForm", boardUpdateForm);
        return "updatePost";
    }

    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @Validated @ModelAttribute BoardUpdateForm boardUpdateForm,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            model.addAttribute("postId", postId);
            return "updatePost";
        }

        BoardUpdateDto boardUpdateDto = boardUpdateForm.createBoardUpdateDto(postId);
        boardService.update(boardUpdateDto);
        return "redirect:/main/board/"+postId;
    }

    @GetMapping("/{postId}/delete")
    public String doUpdating(@PathVariable Long postId) {
        boardService.delete(postId);
        return "redirect:/main/board";
    }

    @GetMapping("/{postId}")
    public String showPosting(@Login LoginForm loginForm, @PathVariable Long postId, Model model) {
        Board board = boardService.findBoard(postId);
        BoardForm boardForm = BoardForm.createBoardForm(board);
        boardService.updateHit(postId);

        model.addAttribute("board", boardForm);

        Optional<Student> student = studentService.findStudent(loginForm.getEmail());
        model.addAttribute("student", student.get());
        return "post";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource processImg(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.createPath(filename, AttachmentType.IMAGE));
    }

    @GetMapping("/attaches/{filename}")
    public ResponseEntity<Resource> processAttaches(@PathVariable String filename, @RequestParam String originName) throws MalformedURLException {
        UrlResource urlResource = new UrlResource("file:" + fileStore.createPath(filename, AttachmentType.GENERAL));

        String encodedUploadFileName = UriUtils.encode(originName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
