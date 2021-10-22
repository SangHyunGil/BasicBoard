package register.demo.domain.like;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import register.demo.domain.board.Board;
import register.demo.domain.student.Student;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name="POSTLIKE_SEQ_GENERATOR",
        sequenceName = "POSTLIKE_SEQ"
)
public class PostLike {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime likeTime;

    @Builder
    public PostLike(Student student, Board board, LocalDateTime likeTime) {
        this.student = student;
        this.board = board;
        this.likeTime = likeTime;
    }
}
