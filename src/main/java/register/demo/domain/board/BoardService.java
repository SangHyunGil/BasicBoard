package register.demo.domain.board;

import java.util.List;

public interface BoardService {

    void post(Board board);

    void update(Long postId, Board board);

    void delete(Long boardId);

    Board findBoard(Long id);

    List<Board> findBoards();
}
