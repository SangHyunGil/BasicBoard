package register.demo.domain.board;

import register.demo.web.board.BoardForm;

import java.util.List;

public interface BoardService {

    void post(Board board);

    void update(Long postId, BoardForm board);

    void delete(Long boardId);

    Board findBoard(Long id);

    List<Board> findBoards();
}
