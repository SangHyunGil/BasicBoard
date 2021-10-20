package register.demo.domain.board;

import org.springframework.data.domain.Sort;
import register.demo.domain.student.Student;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardUpdateForm;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Board post(BoardAddForm boardForm, Student student) throws IOException;

    Boolean update(Long boardId, BoardUpdateForm board);

    Boolean delete(Long boardId);

    Boolean updateHit(Long boardId);

    Board findBoard(Long boardId);

    List<Board> findBoard(String title);

    List<Board> findBoard(Student student);

    List<Board> findBoards(Sort sort);

}
