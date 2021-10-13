package register.demo.domain.board;

import register.demo.domain.student.Student;
import register.demo.web.board.BoardForm;

import java.util.List;

public interface BoardService {

    Long post(Board board);

    Boolean update(Long postId, BoardForm board);

    Boolean delete(Long boardId);

    Board findBoard(Long id);

    List<Board> findBoard(String title);

    List<Board> findBoard(Student student);

    List<Board> findBoards();
}
