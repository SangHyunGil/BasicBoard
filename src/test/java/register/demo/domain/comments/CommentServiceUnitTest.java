package register.demo.domain.comments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardRepository;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.comment.form.CommentAddForm;
import register.demo.web.comment.form.CommentUpdateForm;

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
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        CommentAddForm commentAddForm = new CommentAddForm(boardId, StudentId, null, "테스트 댓글입니다.");
        Comment comment = new Comment(student, board, null, "테스트 댓글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(comment, "id",commentId);

        //mocking
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        //when
        Comment findComment = commentService.addComment(commentAddForm);

        //then
        assertEquals(comment, findComment);
    }

    @Test
    public void 전체댓글조회() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId1 = 1L;
        CommentAddForm commentAddForm1 = new CommentAddForm(boardId, StudentId, null, "테스트1 댓글입니다.");
        Comment comment1 = new Comment(student, board, null, "테스트1 댓글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(comment1, "id",commentId1);

        Long commentId2 = 2L;
        CommentAddForm commentAddForm2 = new CommentAddForm(boardId, StudentId, null, "테스트2 댓글입니다.");
        Comment comment2 = new Comment(student, board, null, "테스트2 댓글입니다.", LocalDateTime.now(), false);
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
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        CommentAddForm commentAddForm = new CommentAddForm(boardId, StudentId, null, "테스트 댓글입니다.");
        Comment comment = new Comment(student, board, null, "테스트 댓글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(comment, "id",commentId);

        //mocking
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(commentRepository.findById(commentId)).willReturn(Optional.ofNullable(comment));


        //when
        commentService.addComment(commentAddForm);
        Boolean isUpdate = commentService.updateComment(commentId, "테스트 댓글 수정입니다.");

        //then
        assertEquals(true, isUpdate);
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        CommentAddForm commentAddForm = new CommentAddForm(boardId, StudentId, null, "테스트 댓글입니다.");
        Comment comment = new Comment(student, board, null, "테스트 댓글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(comment, "id",commentId);

        //mocking
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(commentRepository.findByIdWithParent(commentId)).willReturn(Optional.ofNullable(comment));

        //when
        commentService.addComment(commentAddForm);
        Boolean isDelete = commentService.deleteComment(commentId);

        //then
        assertEquals(true, isDelete);
    }
}
