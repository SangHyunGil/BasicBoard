package register.demo.comments.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDeleteForm {
    private Long commentId;

    public CommentDeleteForm(Long commentId) {
        this.commentId = commentId;
    }
}
