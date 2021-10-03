package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    public void addComment(Long boardId, Comment comment) {
        commentRepository.saveComment(boardId, comment);
    }

    public List<Comment> findComments(Long boardId) {
        return commentRepository.findAllComment(boardId);
    }

    public void deleteComment(Long boardId, Long commentId) {
        commentRepository.deleteComment(boardId, commentId);
    }

    public void updateComment(Long commentId, String content) {
        commentRepository.updateComment(commentId, content);
    }

    public void replyComment(Long parentId, Comment childComment) {
        commentRepository.replyComment(parentId, childComment);
    }
}
