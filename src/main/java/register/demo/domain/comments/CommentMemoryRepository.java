package register.demo.domain.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentMemoryRepository implements CommentRepository{

    private static Long ID = 0L;
    private static Map<Long, Comment> commentMap = new ConcurrentHashMap<>();
    private final BoardRepository boardRepository;

    public void saveComment(Long id, Comment comment) {
        comment.setId(++ID);
        commentMap.put(ID, comment);

        Board board = boardRepository.findById(id);
        board.getComments().add(comment);
    }

    public void deleteComment(Long boardId, Long commentId) {
        Comment comment = commentMap.get(commentId);

        if (comment.getParentId() != null) {
            Comment parentComment = commentMap.get(comment.getParentId());
            if (parentComment.getContent().equals("삭제된 메세지입니다.")) {
                List<Comment> children = parentComment.getChildren();
                children.get(children.indexOf(comment)).setContent("삭제된 메세지입니다.");
            } else {
                parentComment.getChildren().remove(comment);
            }
        } else {
            List<Comment> comments = boardRepository.findById(boardId).getComments();
            Comment deleteComment = comments.get(comments.indexOf(comment));
            List<Comment> commentChildren = deleteComment.getChildren();
            if (!commentChildren.isEmpty()) {
                deleteComment.setContent("삭제된 메세지입니다.");
            } else {
                comments.remove(comment);
            }
        }
    }

    public void updateComment(Long commentId, String content) {
        Comment comment = commentMap.get(commentId);
        comment.setContent(content);
    }

    public void replyComment(Long parentId, Comment childComment) {
        childComment.setId(++ID);
        childComment.setParentId(parentId);
        commentMap.put(ID,childComment);

        Comment parentComment = commentMap.get(parentId);
        parentComment.getChildren().add(childComment);
    }

    private List<Comment> getCommentList(Long boardId) {
        return boardRepository.findById(boardId).getComments();
    }

    public Comment findById(Long id) {
        return commentMap.get(id);
    }

    public List<Comment> findAllComment(Long id) {
        return new ArrayList<>(getCommentList(id));
    }
}
