package register.demo.domain.board;

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

    public Board savePost(Board board) {
        board.setId(++ID);
        boardMap.put(ID, board);
        return board;
    }

    public Board findById(Long id) {
        return boardMap.get(id);
    }

    public List<Board> findAllPost() {
        return new ArrayList<>(boardMap.values());
    }
}
