package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardMemoryRepository;
import register.demo.domain.board.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentMemoryRepository implements CommentRepository{

    private static Long ID = 0L;
    private final BoardRepository boardRepository;

    public void saveComment(Long id, Comment comment) {
        comment.setId(++ID);
        Board board = boardRepository.findById(id);
        board.getComments().add(comment);
    }

    public void deleteComment(Long boardId, Long commentId) {
        List<Comment> comments = boardRepository.findById(boardId).getComments();

        comments.remove(comments.stream()
                .filter(comment -> comment.getId() == commentId)
                .findAny().get());
    }

    public void updateComment(Long boardId, Long commentId, String content) {
        List<Comment> comments = boardRepository.findById(boardId).getComments();

        Comment updateComment = comments.stream()
                .filter(comment -> comment.getId() == commentId)
                .findAny().get();
        updateComment.setContent(content);
        updateComment.setWriteTime(LocalDateTime.now());
    }



    public List<Comment> findAllComment(Long id) {
        return new ArrayList<>(boardRepository.findById(id).getComments());
    }
}
