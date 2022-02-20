package register.demo.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import register.demo.board.domain.Board;
import register.demo.category.domain.CategoryType;
import register.demo.board.controller.dto.BoardPostDto;
import register.demo.board.controller.dto.BoardUpdateDto;
import register.demo.board.controller.dto.HotPostDto;
import register.demo.board.controller.search.SearchCondition;

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
