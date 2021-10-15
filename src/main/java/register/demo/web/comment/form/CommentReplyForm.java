package register.demo.web.comment.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentReplyForm {
    private Long parentId;
    private String content;

    public CommentReplyForm() {
    }
}
