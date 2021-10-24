package register.demo.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import register.demo.web.board.dto.HotPostDto;
import register.demo.web.board.search.SearchCondition;

import java.util.List;

public interface BoardCustomRepository {
    List<Board> search(SearchCondition searchCondition);
    List<HotPostDto> todayHotPost();
    Page<Board> findBoardByPaging(Pageable pageable);
}
