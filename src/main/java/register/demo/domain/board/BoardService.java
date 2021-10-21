package register.demo.domain.board;

import org.springframework.data.domain.Sort;
import register.demo.domain.student.Student;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.dto.BoardUpdateDto;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardUpdateForm;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Board post(BoardPostDto boardPostDto) throws IOException;

    Boolean update(BoardUpdateDto boardUpdateDto);

    Boolean delete(Long boardId);

    Boolean updateHit(Long boardId);

    Board findBoard(Long boardId);

    List<Board> findBoard(String title);

    List<Board> findBoard(Student student);

    List<Board> findBoards(Sort sort);

}
