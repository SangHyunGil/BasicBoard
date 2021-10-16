package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteForm {
    private Long boardId;
    private Long commentId;

    public CommentDeleteForm() {
    }
}
