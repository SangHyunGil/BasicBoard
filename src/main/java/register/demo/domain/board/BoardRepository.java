package register.demo.domain.board;

import register.demo.web.board.BoardForm;

import java.util.List;

public interface BoardRepository {

    void savePost(Board board);

    void updatePost(Long postId, BoardForm boardForm);

    void deletePost(Long boardId);

    Board findById(Long id);

    List<Board> findAllPost();
}
