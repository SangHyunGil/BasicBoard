package register.demo.web.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentAddForm {
    private Long boardId;
    private String content;

    public CommentAddForm() {
    }
}
