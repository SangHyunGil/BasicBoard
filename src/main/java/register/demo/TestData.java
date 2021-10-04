package register.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardMemoryRepository;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentMemoryRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestData {
    private final StudentMemoryRepository studentRepository;
    private final BoardMemoryRepository boardRepository;

    @PostConstruct
    public void init() {
        Student student = new Student("qwe@naver.com", "qwe", "qwe", "qwe", "qwe", "qwe");
        studentRepository.save(student);

        Board board = new Board("게시글1", "상현", "테스트", LocalDateTime.now());
        //board.getComments().add(new Comment("qwe", "qwe", LocalDateTime.now()));
        boardRepository.savePost(board);
    }
}
