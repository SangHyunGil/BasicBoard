package register.demo.comments.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import register.demo.student.domain.Student;

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
