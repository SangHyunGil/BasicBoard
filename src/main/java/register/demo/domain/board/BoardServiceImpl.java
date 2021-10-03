package register.demo.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    public void post(Board board) {
        boardRepository.savePost(board);
    }

    public Board findBoard(Long id) {
        return boardRepository.findById(id);
    }

    public List<Board> findBoards() {
        return boardRepository.findAllPost();
    }
}