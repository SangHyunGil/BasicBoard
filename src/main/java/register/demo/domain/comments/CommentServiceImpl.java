package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.StudentService;
import register.demo.web.comment.form.CommentAddForm;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final StudentService studentService;
    private final BoardService boardService;
    private final CommentRepository commentRepository;

    public Comment addComment(CommentAddForm commentAddForm) {
        return commentRepository.save(makeComment(commentAddForm));
    }

    private Comment makeComment(CommentAddForm commentAddForm) {
        return new Comment(studentService.findStudent(commentAddForm.getStudentId()), boardService.findBoard(commentAddForm.getBoardId()), getParent(commentAddForm), commentAddForm.getContent(), LocalDateTime.now(), false);
    }

    private Comment getParent(CommentAddForm commentAddForm) {
        return commentAddForm.getParentId() != null ? commentRepository.findById(commentAddForm.getParentId()).orElseThrow(() -> new IllegalArgumentException("현재 댓글이 존재하지 않습니다.")) : null;
    }

    public List<Comment> findComments(Long boardId) {
        return commentRepository.findAllComments(boardId);
    }

    public Boolean deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdWithParent(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (comment.getChildren().size() != 0) {
            comment.setIsDeleted(true);
        } else {
            commentRepository.delete(getDeletableAncestorComment(comment));
        }

        return true;
    }

    private Comment getDeletableAncestorComment(Comment comment) {
        log.info("댓글삭제에러, {}", comment.getContent());
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
