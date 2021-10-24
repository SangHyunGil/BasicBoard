package register.demo.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.dto.BoardUpdateDto;
import register.demo.web.board.dto.HotPostDto;
import register.demo.web.board.search.SearchCondition;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Board post(BoardPostDto boardPostDto) throws IOException;

    Boolean update(BoardUpdateDto boardUpdateDto);

    Boolean delete(Long boardId);

    Boolean updateHit(Long boardId);

    Board findBoard(Long boardId);

    List<Board> findBoard(SearchCondition searchCondition);

    Page<Board> findBoards(Pageable pageable);

    List<HotPostDto> findHotPosts();
}
