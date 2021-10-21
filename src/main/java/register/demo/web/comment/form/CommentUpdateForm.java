package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
