package register.demo.web.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import register.demo.domain.board.Board;
import register.demo.domain.file.AttachmentType;
import register.demo.domain.student.Student;
import register.demo.web.board.form.BoardAddForm;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardPostDto {
    private Student writer;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Map<AttachmentType, List<MultipartFile>> attachmentFiles = new ConcurrentHashMap<>();

    @Builder
    public BoardPostDto(Student writer, String title, String content, Map<AttachmentType, List<MultipartFile>> attachmentFiles) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.attachmentFiles = attachmentFiles;
    }

    public Board createBoard() {
        return Board.builder()
                    .writer(writer)
                    .title(title)
                    .writeTime(LocalDateTime.now())
                    .content(content)
                    .attachedFiles(new ArrayList<>())
                    .isDeleted(false)
                    .hit(0)
                    .build();
    }
}
