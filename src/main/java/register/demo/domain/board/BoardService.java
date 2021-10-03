package register.demo.domain.board;

import java.util.List;

public interface BoardService {

    void post(Board board);

    Board findBoard(Long id);

    List<Board> findBoards();
}
