package register.demo.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.board.domain.Board;
import register.demo.board.repository.BoardRepository;
import register.demo.like.domain.PostLike;
import register.demo.like.repository.PostLikeRepository;
import register.demo.like.controller.dto.PostLikeDto;
import register.demo.like.controller.dto.PostLikeResponseDto;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService{

    private final PostLikeRepository postLikeRepository;
    private final BoardRepository boardRepository;

    public Boolean pushLikeButton(PostLikeDto postLikeDto) {
        postLikeRepository.exist(postLikeDto.getStudent().getId(), postLikeDto.getBoardId())
                .ifPresentOrElse(
                        postLike -> postLikeRepository.deleteById(postLike.getId()),
                        () -> {
                            Board board = getBoard(postLikeDto);
                            postLikeRepository.save(new PostLike(postLikeDto.getStudent(), board, LocalDateTime.now()));
                        });

        return true;
    }

    @Transactional(readOnly = true)
    public Board getBoard(PostLikeDto postLikeDto) {
        return boardRepository.findById(postLikeDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public PostLikeResponseDto getPostLikeInfo(PostLikeDto postLikeDto) {
        long postLikeNum = getPostLikeNum(postLikeDto);
        boolean check = checkPushedLike(postLikeDto);

        return new PostLikeResponseDto(postLikeNum, check);
    }

    @Transactional(readOnly = true)
    public boolean checkPushedLike(PostLikeDto postLikeDto) {
        return postLikeRepository.exist(postLikeDto.getStudent().getId(), postLikeDto.getBoardId())
                .isPresent();
    }

    @Transactional(readOnly = true)
    public long getPostLikeNum(PostLikeDto postLikeDto) {
        return postLikeRepository.findPostLikeNum(postLikeDto.getBoardId());
    }
}
