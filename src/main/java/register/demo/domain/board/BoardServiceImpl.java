package register.demo.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.web.board.BoardForm;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    public void post(Board board) {
        boardRepository.savePost(board);
    }

    public void update(Long postId, BoardForm boardForm) {
        boardRepository.updatePost(postId, boardForm);
    }

    public void delete(Long boardId) {
        boardRepository.deletePost(boardId);

    }

    public Board findBoard(Long id) {
        return boardRepository.findById(id);
    }

    public List<Board> findBoards() {
        return boardRepository.findAllPost();
    }


}
