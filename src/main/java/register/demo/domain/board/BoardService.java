package register.demo.domain.board;

import org.springframework.data.domain.Sort;
import register.demo.domain.student.Student;
import register.demo.web.board.BoardForm;

import java.util.List;

public interface BoardService {

    Board post(Board board);

    Boolean update(Long boardId, BoardForm board);

    Boolean delete(Long boardId);

    Boolean updateHit(Long boardId);

    Board findBoard(Long boardId);

    List<Board> findBoard(String title);

    List<Board> findBoard(Student student);

    List<Board> findBoards(Sort sort);

}
