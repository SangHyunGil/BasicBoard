package register.demo.like.service;

import register.demo.like.controller.dto.PostLikeDto;
import register.demo.like.controller.dto.PostLikeResponseDto;

public interface PostLikeService {
    Boolean pushLikeButton(PostLikeDto postLikeDto);
    PostLikeResponseDto getPostLikeInfo(PostLikeDto postLikeDto);
}
