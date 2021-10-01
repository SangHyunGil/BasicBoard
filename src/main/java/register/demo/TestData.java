package register.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;
import register.demo.domain.comments.Comment;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestData {
    private final StudentRepository studentRepository;
    private final BoardRepository boardRepository;

    @PostConstruct
    public void init() {
        Student student = new Student("qwe@naver.com", "qwe", "qwe", "qwe", "qwe", "qwe");
        studentRepository.save(student);

        Board board = new Board("Test", "상현", "test", LocalDateTime.now());
        //board.getComments().add(new Comment("qwe", "qwe", LocalDateTime.now()));
        boardRepository.savePost(board);
    }
}
