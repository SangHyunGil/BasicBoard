package register.demo.domain.board;

import java.util.List;

public interface BoardRepository {

    void savePost(Board board);

    void updatePost(Long postId, Board board);

    void deletePost(Long boardId);

    Board findById(Long id);

    List<Board> findAllPost();
}
