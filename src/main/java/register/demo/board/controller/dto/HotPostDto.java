package register.demo.board.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import register.demo.board.domain.Board;

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
