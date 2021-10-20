package register.demo.web.board.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import register.demo.domain.board.Board;
import register.demo.domain.file.Attachment;
import register.demo.domain.student.Student;

import javax.validation.constraints.NotBlank;
import java.io.Console;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardAddForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private List<MultipartFile> imageFiles;
    private List<MultipartFile> generalFiles;

    public Board createBoard(Student writer) {
        return Board.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .isDeleted(false)
                .writeTime(LocalDateTime.now())
                .attachedFiles(new ArrayList<>())
                .hit(0)
                .build();
    }

    @Builder
    public BoardAddForm(String title, String content, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.title = title;
        this.content = content;
        this.imageFiles = imageFiles;
        this.generalFiles = generalFiles;
    }
}
