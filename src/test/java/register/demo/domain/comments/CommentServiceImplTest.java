package register.demo.domain.comments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    StudentService studentService;
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;

    @Test
    public void 댓글달기() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);
        Student studentA = studentService.findStudent("testId@gmail.com").get();

        Board board = new Board("테스트 글", studentA, "테스트 글입니다.", LocalDateTime.now(), false);
        Comment comment = new Comment(studentA, "테스트 댓글입니다.", LocalDateTime.now(), false);

        //when
        Long postId = boardService.post(board);
        commentService.addComment(postId, comment);

        //then
        assertEquals(1, commentService.findComments(postId).size());
    }

    @Test
    public void 전체댓글조회() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);
        Student studentA = studentService.findStudent("testId@gmail.com").get();

        Board board = new Board("테스트 글", studentA, "테스트 글입니다.", LocalDateTime.now(), false);
        Comment comment1 = new Comment(studentA, "첫 번째 테스트 댓글입니다.", LocalDateTime.now(), false);
        Comment comment2 = new Comment(studentA, "두 번째 테스트 댓글입니다.", LocalDateTime.now(), false);
        Comment comment3 = new Comment(studentA, "세 번째 테스트 댓글입니다.", LocalDateTime.now(), false);

        Long postId = boardService.post(board);
        commentService.addComment(postId, comment1);
        commentService.addComment(postId, comment2);
        commentService.addComment(postId, comment3);

        //when
        List<Comment> comments = commentService.findComments(board.getId());

        //then
        assertEquals(3, comments.size());
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);
        Student studentA = studentService.findStudent("testId@gmail.com").get();

        Board board = new Board("테스트 글", studentA, "테스트 글입니다.", LocalDateTime.now(), false);
        Comment comment = new Comment(studentA, "테스트 댓글입니다.", LocalDateTime.now(), false);

        //when
        Long postId = boardService.post(board);
        Long commentId = commentService.addComment(postId, comment);
        commentService.updateComment(commentId, "테스트 댓글 수정하였습니다.");

        //then
        assertEquals("테스트 댓글 수정하였습니다.", commentService.findComments(postId).get(0).getContent());
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);
        Student studentA = studentService.findStudent("testId@gmail.com").get();

        Board board = new Board("테스트 글", studentA, "테스트 글입니다.", LocalDateTime.now(), false);
        Comment comment = new Comment(studentA, "테스트 댓글입니다.", LocalDateTime.now(), false);

        //when
        Long postId = boardService.post(board);
        commentService.addComment(postId, comment);

        Comment findComment = commentService.findComments(board.getId()).get(0);
        Boolean isDelete = commentService.deleteComment(postId, findComment.getId());

        //then
        assertEquals(true, isDelete);
    }
}