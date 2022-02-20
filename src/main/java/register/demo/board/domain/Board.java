package register.demo.board.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import register.demo.category.domain.Category;
import register.demo.file.domain.Attachment;
import register.demo.student.domain.Student;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@SequenceGenerator(
        name="BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Attachment> attachedFiles = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writeTime;
    private Boolean isDeleted;
    private String title;
    private String content;
    private Integer hit;

    @Builder
    public Board(Student writer, Category category, List<Attachment> attachedFiles, LocalDateTime writeTime, Boolean isDeleted, String title, String content, Integer hit) {
        this.writer = writer;
        this.category = category;
        this.attachedFiles = attachedFiles;
        this.writeTime = writeTime;
        this.isDeleted = isDeleted;
        this.title = title;
        this.content = content;
        this.hit = hit;
    }

    public void setAttachment(Attachment attachment) {
        this.attachedFiles.add(attachment);
        attachment.setBoard(this);
    }
}
