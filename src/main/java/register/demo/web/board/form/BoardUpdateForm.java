package register.demo.web.board.form;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BoardUpdateForm {
    @NotBlank
    private String title;
    private String writer;
    @NotBlank
    private String content;

    @Builder
    public BoardUpdateForm(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
