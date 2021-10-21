package register.demo.web.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime writeTime;
    private Boolean isDelete;
    private List<CommentDto> children = new ArrayList<>();

    public CommentDto(Long id, String writer, String content, LocalDateTime writeTime, Boolean isDelete) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.writeTime = writeTime;
        this.isDelete = isDelete;
    }
}