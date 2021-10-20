package register.demo.domain.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import register.demo.domain.student.Student;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name="BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ"
)
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student writer;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeTime;
    private Boolean isDeleted;
    private String title;
    private String content;
    private Integer hit;

    protected Board() {
    }

    public Board(String title, Student writer, String content, LocalDateTime writeTime, Boolean isDelete, Integer hit) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.writeTime = writeTime;
        this.isDeleted = isDelete;
        this.hit = hit;
    }


}
