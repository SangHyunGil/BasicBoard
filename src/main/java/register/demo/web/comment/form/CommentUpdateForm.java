package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class CommentUpdateForm {
    private Long commentId;
    private String content;

    public CommentUpdateForm() {
    }
}
