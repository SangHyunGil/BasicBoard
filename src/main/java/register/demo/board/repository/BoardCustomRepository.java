package register.demo.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import register.demo.board.domain.Board;
import register.demo.category.domain.CategoryType;
import register.demo.board.controller.dto.HotPostDto;
import register.demo.board.controller.search.SearchCondition;

import java.util.List;

public interface BoardCustomRepository {
    List<HotPostDto> todayHotPost();
    Page<Board> search(SearchCondition searchCondition, Pageable pageable);
    Page<Board> findBoardByPaging(Pageable pageable);
    Page<Board> classifyByCategory(CategoryType categoryType, Pageable pageable);
}
