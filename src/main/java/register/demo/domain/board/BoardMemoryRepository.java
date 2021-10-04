package register.demo.domain.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import register.demo.domain.comments.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BoardMemoryRepository implements BoardRepository{

    private static Long ID = 0L;
    private static Map<Long, Board> boardMap = new ConcurrentHashMap<>();

    public void savePost(Board board) {
        board.setId(++ID);
        boardMap.put(ID, board);
    }

    public void updatePost(Long postId, Board board) {
        List<Comment> comments = boardMap.get(postId).getComments();
        board.setId(postId);
        board.setComments(comments);
        boardMap.remove(postId);
        boardMap.put(postId, board);
    }

    public void deletePost(Long boardId) {
        boardMap.remove(boardId);
    }

    public Board findById(Long id) {
        return boardMap.get(id);
    }

    public List<Board> findAllPost() {
        return new ArrayList<>(boardMap.values());
    }
}
