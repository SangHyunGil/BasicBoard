package register.demo.domain.comments;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private static Long ID = 0L;
    private final BoardRepository boardRepository;

    public void saveComment(Long id, Comment comment) {
        comment.setId(++ID);
        Board board = boardRepository.findById(id);
        board.getComments().add(comment);
    }

    public List<Comment> findAllComment(Long id) {
        return new ArrayList<>(boardRepository.findById(id).getComments());
    }
}
