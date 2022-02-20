package register.demo.file.domain;

import lombok.*;
import register.demo.board.domain.Board;
import register.demo.file.AttachmentType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name="ATTACHMENT_SEQ_GENERATOR",
        sequenceName = "ATTACHMENT_SEQ"
)
public class Attachment {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "attachment_id")
    private Long id;
    private String originFilename;
    private String storeFilename;
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Attachment(Long id, String originFileName, String storePath, AttachmentType attachmentType) {
        this.id = id;
        this.originFilename = originFileName;
        this.storeFilename = storePath;
        this.attachmentType = attachmentType;
    }
}

