package register.demo.web.board.form;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import register.demo.web.board.dto.BoardUpdateDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
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

    public BoardUpdateDto createBoardUpdateDto(Long boardId) {
        return BoardUpdateDto.builder()
                    .boardId(boardId)
                    .title(title)
                    .content(content)
                    .build();
    }
}
