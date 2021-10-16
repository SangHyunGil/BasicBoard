package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateForm {
    private Long commentId;
    private String content;

    public CommentUpdateForm() {
    }
}
