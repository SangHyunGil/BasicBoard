package register.demo.domain.comments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.comment.dto.CommentAddDto;
import register.demo.web.comment.form.CommentAddForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentRepository commentRepository;
    @Mock
    BoardService boardService;
    @Mock
    StudentService studentService;

    @Test
    public void 댓글달기() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        CommentAddForm commentAddForm = CommentAddForm.builder().content("테스트 댓글입니다.").parentId(null).build();
        CommentAddDto commentAddDto = commentAddForm.createCommentAddDto(board.getId(), student);
        Comment comment = Comment.builder()
                .writer(student)
                .board(board)
                .parent(null)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(comment, "id",commentId);

        //mocking
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        //when
        Comment findComment = commentService.addComment(commentAddDto);

        //then
        assertEquals(comment, findComment);
    }

    @Test
    public void 전체댓글조회() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId1 = 1L;
        Comment comment1 = Comment.builder()
                .writer(student)
                .board(board)
                .parent(null)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(comment1, "id",commentId1);

        Long commentId2 = 2L;
        Comment comment2 = Comment.builder()
                .writer(student)
                .board(board)
                .parent(null)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(comment2, "id",commentId2);

        //mocking
        given(commentRepository.findAllComments(boardId)).willReturn(new ArrayList<>(Arrays.asList(comment1, comment2)));

        //when
        List<Comment> findComments = commentService.findComments(boardId);

        //then
        assertEquals(2, findComments.size());
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        Comment comment = Comment.builder()
                .writer(student)
                .board(board)
                .parent(null)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(comment, "id",commentId);

        //mocking
        given(commentRepository.findById(commentId)).willReturn(Optional.ofNullable(comment));


        //when
        Boolean isUpdate = commentService.updateComment(commentId, "테스트 댓글 수정입니다.");

        //then
        assertEquals(true, isUpdate);
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        Comment comment = Comment.builder()
                .writer(student)
                .board(board)
                .parent(null)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(comment, "id",commentId);

        //mocking
        given(commentRepository.findByIdWithParent(commentId)).willReturn(Optional.ofNullable(comment));

        //when
        Boolean isDelete = commentService.deleteComment(commentId);

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void 답글달기() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        Long parentCommentId = 1L;
        Comment parentComment = Comment.builder()
                .writer(student)
                .board(board)
                .parent(null)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(parentComment, "id",parentCommentId);

        Long childCommentId = 2L;
        CommentAddForm childCommentAddForm = CommentAddForm.builder().content("테스트 댓글입니다.").parentId(null).build();
        CommentAddDto childCommentAddDto = childCommentAddForm.createCommentAddDto(board.getId(), student);
        Comment childComment = Comment.builder()
                .writer(student)
                .board(board)
                .parent(parentComment)
                .content("테스트 댓글입니다.")
                .writeTime(LocalDateTime.now())
                .isDelete(false)
                .build();
        ReflectionTestUtils.setField(childComment, "id",childCommentId);

        //mocking
        given(commentRepository.save(any(Comment.class))).willReturn(childComment);

        //when
        Comment replyComment = commentService.addComment(childCommentAddDto);

        //then
        assertEquals(parentComment, replyComment.getParent());
    }
}
