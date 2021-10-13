package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    public Long addComment(Long boardId, Comment comment) {
        return commentRepository.saveComment(boardId, comment);
    }

    public List<Comment> findComments(Long boardId) {
        return commentRepository.findAllComment(boardId);
    }

    public Boolean deleteComment(Long boardId, Long commentId) {
        commentRepository.deleteComment(boardId, commentId);
        return true;
    }

    public Boolean updateComment(Long commentId, String content) {
        commentRepository.updateComment(commentId, content);
        return true;
    }

    public Boolean replyComment(Long parentId, Comment childComment) {
        commentRepository.replyComment(parentId, childComment);
        return true;
    }
}
