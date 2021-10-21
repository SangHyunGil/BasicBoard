package register.demo.web.comment.form;

import lombok.*;
import register.demo.domain.student.Student;
import register.demo.web.comment.dto.CommentAddDto;

@Data
@NoArgsConstructor
public class CommentAddForm {
    private Long parentId;
    private String content;

    public CommentAddDto createCommentAddDto(Long boardId, Student student) {
        return register.demo.web.comment.dto.CommentAddDto.builder()
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
