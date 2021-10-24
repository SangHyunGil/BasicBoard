package register.demo.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import register.demo.domain.category.Category;
import register.demo.domain.category.CategoryType;
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

    Page<Board> findBoards(SearchCondition searchCondition, Pageable pageable);

    Page<Board> findBoards(CategoryType categoryType, Pageable pageable);

    Page<Board> findBoards(Pageable pageable);

    List<HotPostDto> findHotPosts();
}
