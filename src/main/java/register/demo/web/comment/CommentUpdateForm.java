package register.demo.web.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateForm {
    private Long commentId;
    private String content;
}
