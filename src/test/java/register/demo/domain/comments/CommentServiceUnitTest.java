package register.demo.domain.comments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.domain.board.Board;
import register.demo.domain.student.Student;
import register.demo.web.comment.CommentUpdateForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentRepository commentRepository;

    @Test
    public void 댓글달기() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        Comment comment = new Comment(student, "테스트 댓글입니다.", LocalDateTime.now(), false);

        //mocking
        given(commentRepository.saveComment(boardId, comment)).willReturn(commentId);
        given(commentRepository.findAllComment(boardId)).willReturn(new ArrayList<>(Arrays.asList(comment)));

        //when
        commentService.addComment(board.getId(), comment);
        Comment findComment = commentService.findComments(boardId).get(0);

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
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId1 = 1L;
        Comment comment1 = new Comment(student, "테스트1 댓글입니다.", LocalDateTime.now(), false);
        Long commentId2 = 2L;
        Comment comment2 = new Comment(student, "테스트2 댓글입니다.", LocalDateTime.now(), false);


        //mocking
        given(commentRepository.saveComment(boardId, comment1)).willReturn(commentId1);
        given(commentRepository.saveComment(boardId, comment2)).willReturn(commentId2);
        given(commentRepository.findAllComment(boardId)).willReturn(new ArrayList<>(Arrays.asList(comment1, comment2)));

        //when
        commentService.addComment(board.getId(), comment1);
        commentService.addComment(board.getId(), comment2);
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
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        Comment comment = new Comment(student, "테스트 댓글입니다.", LocalDateTime.now(), false);
        CommentUpdateForm commentUpdateForm = new CommentUpdateForm(commentId, "테스트 댓글 수정입니다.");

        //mocking
        given(commentRepository.saveComment(boardId, comment)).willReturn(commentId);
        given(commentRepository.updateComment(commentId, commentUpdateForm.getContent())).willReturn(true);

        //when
        commentService.addComment(board.getId(), comment);
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
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        ReflectionTestUtils.setField(board, "id", boardId);

        Long commentId = 1L;
        Comment comment = new Comment(student, "테스트 댓글입니다.", LocalDateTime.now(), false);

        //mocking
        given(commentRepository.saveComment(boardId, comment)).willReturn(commentId);
        given(commentRepository.deleteComment(boardId, commentId)).willReturn(true);

        //when
        commentService.addComment(board.getId(), comment);
        Boolean isDelete = commentService.deleteComment(boardId, commentId);

        //then
        assertEquals(true, isDelete);
    }
}
