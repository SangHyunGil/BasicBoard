package register.demo.comments.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import register.demo.board.domain.Board;
import register.demo.student.domain.Student;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "COMMENT_SEQ_GENERATOR",
        sequenceName = "COMMENT_SEQ"
)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ_GENERATOR")
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Student writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    private String content;
    private LocalDateTime writeTime;
    private Boolean isDeleted = false;

    protected Comment() {
    }

    @Builder
    public Comment(Student writer, Board board, Comment parent, String content, LocalDateTime writeTime, Boolean isDelete) {
        this.writer = writer;
        this.board = board;
        this.parent = parent;
        this.content = content;
        this.writeTime = writeTime;
        this.isDeleted = isDelete;
    }
}
