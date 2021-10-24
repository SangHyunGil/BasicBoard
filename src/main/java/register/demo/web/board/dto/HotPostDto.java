package register.demo.web.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import register.demo.domain.board.Board;

@Data
@NoArgsConstructor
public class HotPostDto {
    private Board board;
    private Long num;

    public HotPostDto(Board board, Long num) {
        this.board = board;
        this.num = num;
    }
}
