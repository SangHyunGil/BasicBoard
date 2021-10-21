package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDeleteForm {
    private Long commentId;

    public CommentDeleteForm(Long commentId) {
        this.commentId = commentId;
    }
}
