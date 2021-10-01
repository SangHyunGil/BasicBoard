package register.demo.web.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentForm {
    private String content;

    public CommentForm() {
    }
}
