package register.demo.domain.board;

import java.util.List;

public interface BoardRepository {

    void savePost(Board board);

    Board findById(Long id);

    List<Board> findAllPost();
}
