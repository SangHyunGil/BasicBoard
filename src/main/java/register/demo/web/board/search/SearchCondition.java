package register.demo.web.board.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCondition {
    private String content;
    private SearchType type;

    public SearchCondition(String content, SearchType type) {
        this.content = content;
        this.type = type;
    }
}
