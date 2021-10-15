package register.demo.web.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class cCommentDto {
    private Long id;
    private String writer;
    private String content;
    private LocalDateTime writeTime;
    private Boolean isDelete;

    public cCommentDto(Long id, String writer, String content, LocalDateTime writeTime, Boolean isDelete) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.writeTime = writeTime;
        this.isDelete = isDelete;
    }
}
