package register.demo.web.comment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import register.demo.domain.student.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentAddDto {

    private Long boardId;
    private Long parentId;
    private Student student;
    private String content;

    @Builder
    public CommentAddDto(Long boardId, Long parentId, Student student, String content) {
        this.boardId = boardId;
        this.parentId = parentId;
        this.student = student;
        this.content = content;
    }
}
