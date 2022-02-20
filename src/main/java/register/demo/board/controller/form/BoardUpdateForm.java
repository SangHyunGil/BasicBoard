package register.demo.board.controller.form;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import register.demo.board.controller.dto.BoardUpdateDto;

import javax.validation.constraints.NotBlank;

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
