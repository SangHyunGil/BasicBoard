package register.demo.web.postlike.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import register.demo.domain.student.Student;

@Data
@NoArgsConstructor
public class PostLikeDto {
    private Student student;
    private Long boardId;

    @Builder
    public PostLikeDto(Student student, Long boardId) {
        this.student = student;
        this.boardId = boardId;
    }
}
