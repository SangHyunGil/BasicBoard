package register.demo.like;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.board.domain.Board;
import register.demo.board.repository.BoardRepository;
import register.demo.like.domain.PostLike;
import register.demo.like.repository.PostLikeRepository;
import register.demo.like.service.PostLikeServiceImpl;
import register.demo.student.domain.Student;
import register.demo.like.controller.dto.PostLikeDto;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class PostLikeServiceUnitTest {

    @InjectMocks
    PostLikeServiceImpl postLikeService;

    @Mock
    PostLikeRepository postLikeRepository;
    @Mock
    BoardRepository boardRepository;

    @Test
    public void 좋아요() throws Exception {
        //given
        Long studentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .name("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트 글")
                .content("테스트 글입니다.")
                .isDeleted(false)
                .writeTime(LocalDateTime.now())
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        PostLikeDto postLikeDto = new PostLikeDto(student, board.getId());
        PostLike postLike = new PostLike(student, board, LocalDateTime.now());

        //mocking
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));
        given(postLikeRepository.exist(studentId, boardId)).willReturn(Optional.empty());
        given(postLikeRepository.save(any(PostLike.class))).willReturn(postLike);

        //when
        Boolean result = postLikeService.pushLikeButton(postLikeDto);

        //then
        assertEquals(true, result);
    }

    @Test
    public void 좋아요취소() throws Exception {
        //given
        Long studentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .name("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트 글")
                .content("테스트 글입니다.")
                .isDeleted(false)
                .writeTime(LocalDateTime.now())
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        PostLikeDto postLikeDto = new PostLikeDto(student, board.getId());
        PostLike postLike = new PostLike(student, board, LocalDateTime.now());

        //mocking
        given(postLikeRepository.exist(studentId, boardId)).willReturn(Optional.ofNullable(postLike));
        willDoNothing().given(postLikeRepository).deleteById(any());

        //when
        Boolean result = postLikeService.pushLikeButton(postLikeDto);

        //then
        assertEquals(true, result);
    }

    @Test
    public void 좋아요개수조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .name("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트 글")
                .content("테스트 글입니다.")
                .isDeleted(false)
                .writeTime(LocalDateTime.now())
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        PostLikeDto postLikeAddDto = new PostLikeDto(student, boardId);

        //mocking
        given(postLikeRepository.exist(studentId, boardId)).willReturn(Optional.empty());
        given(postLikeRepository.findPostLikeNum(boardId)).willReturn(1L);

        //when
        long postLikeNum = postLikeService.getPostLikeInfo(postLikeAddDto).getPostLikeNum();

        //then
        assertEquals(1, postLikeNum);
    }
}
