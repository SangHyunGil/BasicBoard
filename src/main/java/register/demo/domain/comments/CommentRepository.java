package register.demo.domain.comments;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public void deleteComment(Long boardId, Long commentId) {
        List<Comment> comments = boardRepository.findById(boardId).getComments();
        for (Comment comment : comments) {
            log.info("comment.getId() = {}, {}", comment.getId(), commentId);
        }
        comments.remove(comments.stream()
                .filter(comment -> comment.getId() == commentId)
                .findAny().get());

    }

    public List<Comment> findAllComment(Long id) {
        return new ArrayList<>(boardRepository.findById(id).getComments());
    }
}
