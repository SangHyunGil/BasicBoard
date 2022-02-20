package register.demo.comments.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentUpdateForm {
    private Long commentId;
    private String content;

    public CommentUpdateForm(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
