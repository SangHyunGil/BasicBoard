package register.demo.comments.controller.form;

import lombok.*;
import register.demo.student.domain.Student;
import register.demo.comments.controller.dto.CommentAddDto;

@Data
@NoArgsConstructor
public class CommentAddForm {
    private Long parentId;
    private String content;

    public CommentAddDto createCommentAddDto(Long boardId, Student student) {
        return register.demo.comments.controller.dto.CommentAddDto.builder()
                    .boardId(boardId)
                    .student(student)
                    .parentId(parentId)
                    .content(content)
                    .build();
    }

    @Builder
    public CommentAddForm(String content, Long parentId) {
        this.content = content;
        this.parentId = parentId;
    }
}
