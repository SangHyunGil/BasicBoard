package register.demo.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.student.Student;
import register.demo.web.board.BoardForm;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    public Long post(Board board) {
        return boardRepository.savePost(board);
    }

    public Boolean update(Long postId, BoardForm boardForm) {
        boardRepository.updatePost(postId, boardForm);
        return true;
    }

    public Boolean delete(Long boardId) {
        boardRepository.deletePost(boardId);
        return true;
    }

    public Board findBoard(Long id) {
        return boardRepository.findById(id);
    }

    public List<Board> findBoard(String title) {
        return boardRepository.findByTitle(title);
    }

    public List<Board> findBoard(Student student) {
        return boardRepository.findByStudent(student);
    }

    public List<Board> findBoards() {
        return boardRepository.findAllPost();
    }


}
