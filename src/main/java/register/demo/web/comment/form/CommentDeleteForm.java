package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class CommentDeleteForm {
    private Long boardId;
    private Long commentId;

    public CommentDeleteForm() {
    }
}
