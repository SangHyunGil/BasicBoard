package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;
import register.demo.web.comment.dto.CommentAddDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Comment addComment(CommentAddDto commentAddDto) {
        return commentRepository.save(makeComment(commentAddDto));
    }

    private Comment makeComment(CommentAddDto commentAddDto) {
        Board board = boardRepository.findById(commentAddDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new Comment(commentAddDto.getStudent(), board, getParent(commentAddDto.getParentId()), commentAddDto.getContent(), LocalDateTime.now(), false);
    }

    private Comment getParent(Long parentId) {
        return parentId != null ? commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("현재 댓글이 존재하지 않습니다.")) : null;
    }

    @Transactional(readOnly = true)
    public List<Comment> findComments(Long boardId) {
        return commentRepository.findAllComments(boardId);
    }

    public Boolean deleteAll(Long boardId) {
        commentRepository.deleteByBoardId(boardId);
        return true;
    }

    public Boolean deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdWithParent(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (comment.getChildren().size() != 0) {
            comment.setIsDeleted(true);
        } else {
            doDelete(getDeletableAncestorComment(comment));
        }
        return true;
    }

    private void doDelete(Comment comment) {
        if (comment.getParent() != null) {
            comment.getParent().getChildren().remove(comment);
        }
        commentRepository.delete(comment);
    }

    private Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if (parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted())
            return getDeletableAncestorComment(parent);
        return comment;
    }

    public Boolean updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.setContent(content);
        return true;
    }
}
