package register.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class TestData {
    private final StudentService studentService;
    private final BoardService boardService;

    @PostConstruct
    public void init() {
        Student student = new Student("qwe@naver.com", "qwe", "qwe", "qwe", "qwe", "qwe");
        studentService.join(student);

        Board board = new Board("게시글1", student, "테스트", LocalDateTime.now(), false, 0);
        //board.getComments().add(new Comment("qwe", "qwe", LocalDateTime.now()));
        boardService.post(board);
    }
}
