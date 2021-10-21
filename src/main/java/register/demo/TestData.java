package register.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.form.BoardAddForm;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class TestData {
    private final StudentService studentService;
    private final BoardService boardService;

    @PostConstruct
    public void init() throws IOException {
        Student student = new Student("qwe@naver.com", "qwe", "qwe", "qwe", "qwe", "qwe");
        studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("게시글1", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(student);
        Board board = boardService.post(boardPostDto);
        //board.getComments().add(new Comment("qwe", "qwe", LocalDateTime.now()));
    }
}
