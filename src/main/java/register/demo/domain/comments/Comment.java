package register.demo.domain.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Comment {
    private Long id;
    private String writer;
    private String Content;
    //private List<Comment> children = new ArrayList<>();
    private LocalDateTime writeTime;

    public Comment(String writer, String content, LocalDateTime writeTime) {
        this.writer = writer;
        Content = content;
        this.writeTime = writeTime;
    }
}
