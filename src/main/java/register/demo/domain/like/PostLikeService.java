package register.demo.domain.like;

import register.demo.web.postlike.dto.PostLikeDto;
import register.demo.web.postlike.dto.PostLikeResponseDto;

public interface PostLikeService {
    Boolean pushLikeButton(PostLikeDto postLikeDto);
    PostLikeResponseDto getPostLikeInfo(PostLikeDto postLikeDto);
}
