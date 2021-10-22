package register.demo.domain.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;
import register.demo.web.postlike.dto.PostLikeDto;
import register.demo.web.postlike.dto.PostLikeResponseDto;

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

    private Board getBoard(PostLikeDto postLikeDto) {
        return boardRepository.findById(postLikeDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    public PostLikeResponseDto getPostLikeInfo(PostLikeDto postLikeDto) {
        long postLikeNum = getPostLikeNum(postLikeDto);
        boolean check = checkPushedLike(postLikeDto);

        return new PostLikeResponseDto(postLikeNum, check);
    }

    private boolean checkPushedLike(PostLikeDto postLikeDto) {
        return postLikeRepository.exist(postLikeDto.getStudent().getId(), postLikeDto.getBoardId())
                .isPresent();
    }

    private long getPostLikeNum(PostLikeDto postLikeDto) {
        return postLikeRepository.findPostLikeNum(postLikeDto.getBoardId());
    }

}
