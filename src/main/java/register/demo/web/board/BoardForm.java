package register.demo.web.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class BoardForm {
    @NotBlank
    private String title;
    private String writer;
    @NotBlank
    private String content;
}
