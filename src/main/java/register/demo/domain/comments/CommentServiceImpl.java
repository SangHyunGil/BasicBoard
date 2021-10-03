package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    public void reply(Long id, Comment comment) {
        commentRepository.saveComment(id, comment);
    }

    public List<Comment> findComments(Long id) {
        return commentRepository.findAllComment(id);
    }

    public void delete(Long boardId, Long commentId) {
        commentRepository.deleteComment(boardId, commentId);
    }

    public void update(Long boardId, Long commentId, String content) {
        commentRepository.updateComment(boardId, commentId, content);
    }
}
