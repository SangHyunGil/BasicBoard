package register.demo.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import register.demo.domain.category.Category;
import register.demo.domain.category.CategoryType;
import register.demo.web.board.dto.HotPostDto;
import register.demo.web.board.search.SearchCondition;

import java.util.List;

public interface BoardCustomRepository {
    List<HotPostDto> todayHotPost();
    Page<Board> search(SearchCondition searchCondition, Pageable pageable);
    Page<Board> findBoardByPaging(Pageable pageable);
    Page<Board> classifyByCategory(CategoryType categoryType, Pageable pageable);
}
