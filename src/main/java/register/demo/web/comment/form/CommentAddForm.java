package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class CommentAddForm {
    private Long boardId;
    private Long studentId;
    private Long parentId;
    private String content;

    public CommentAddForm() {
    }
}
