package register.demo.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import register.demo.domain.comments.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {

    private Long id;
    private String title;
    private String writer;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeTime;
    private List<Comment> comments = new ArrayList<>();

    public Board(String title, String writer, String content, LocalDateTime writeTime) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.writeTime = writeTime;
    }
}
