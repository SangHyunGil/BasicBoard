package register.demo.web.comment.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class pCommentDto {
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime writeTime;
    private Boolean isDelete;
    private List<cCommentDto> children;

    public pCommentDto(Long id, String writer, String content, LocalDateTime writeTime, Boolean isDelete, List<cCommentDto> children) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.writeTime = writeTime;
        this.isDelete = isDelete;
        this.children = children;
    }
}
