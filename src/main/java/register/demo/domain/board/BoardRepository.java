package register.demo.domain.board;

import java.util.List;

public interface BoardRepository {

    Board savePost(Board board);

    Board findById(Long id);

    List<Board> findAllPost();
}
