package register.demo.like.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeResponseDto {
    long postLikeNum;
    Boolean checkLiked;

    @Builder
    public PostLikeResponseDto(long postLikeNum, Boolean checkLiked) {
        this.postLikeNum = postLikeNum;
        this.checkLiked = checkLiked;
    }
}
