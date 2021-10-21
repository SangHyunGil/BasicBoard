package register.demo.domain.board;

import java.util.List;

public interface BoardCustomRepository {

    public List<Board> search(SearchCondition searchCondition);
}
