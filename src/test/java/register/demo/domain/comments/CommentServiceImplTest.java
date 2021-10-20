package register.demo.domain.comments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.comment.form.CommentAddForm;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    EntityManager em;
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
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        Board board = boardService.post(boardAddForm, student);

        CommentAddForm commentAddForm = new CommentAddForm(board.getId(), joinStudent.getId(), null, "테스트 댓글입니다.");

        //when

        commentService.addComment(commentAddForm);

        //then
        assertEquals(1, commentService.findComments(board.getId()).size());
    }

    @Test
    public void 전체댓글조회() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        Board board = boardService.post(boardAddForm, student);

        CommentAddForm commentAddForm1 = new CommentAddForm(board.getId(), joinStudent.getId(), null, "첫 번째 테스트 댓글입니다.");
        CommentAddForm commentAddForm2 = new CommentAddForm(board.getId(), joinStudent.getId(), null, "두 번째 테스트 댓글입니다.");
        CommentAddForm commentAddForm3 = new CommentAddForm(board.getId(), joinStudent.getId(), null, "세 번째 테스트 댓글입니다.");

        commentService.addComment(commentAddForm1);
        commentService.addComment(commentAddForm2);
        commentService.addComment(commentAddForm3);

        //when
        List<Comment> comments = commentService.findComments(board.getId());
        for (Comment comment : comments) {
            System.out.println("comment.getContent() = " + comment.getContent());
        }
        //then
        assertEquals(3, comments.size());
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        Board board = boardService.post(boardAddForm, student);

        CommentAddForm commentAddForm = new CommentAddForm(board.getId(), joinStudent.getId(), null, "테스트 댓글입니다.");

        //when
        Comment comment = commentService.addComment(commentAddForm);
        commentService.updateComment(comment.getId(), "테스트 댓글 수정하였습니다.");

        //then
        assertEquals("테스트 댓글 수정하였습니다.", commentService.findComments(board.getId()).get(0).getContent());
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        Board board = boardService.post(boardAddForm, student);

        CommentAddForm commentAddForm = new CommentAddForm(board.getId(), joinStudent.getId(), null, "테스트 댓글입니다.");

        //when
        commentService.addComment(commentAddForm);

        Comment findComment = commentService.findComments(board.getId()).get(0);
        Boolean isDelete = commentService.deleteComment(findComment.getId());

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void 답글달기() throws Exception {
        //given
        Student student = new Student("testId@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        Board board = boardService.post(boardAddForm, student);

        CommentAddForm commentAddForm1 = new CommentAddForm(board.getId(), joinStudent.getId(), null, "테스트 댓글입니다.");
        Comment parentComment = commentService.addComment(commentAddForm1);

        //when
        CommentAddForm commentAddForm2 = new CommentAddForm(board.getId(), joinStudent.getId(), parentComment.getId(), "테스트 댓글입니다.");
        Comment childComment = commentService.addComment(commentAddForm2);

        //then
        assertEquals(parentComment, childComment.getParent());
    }

    @Test
    public void N_플러스_1_문제() throws Exception {
        // 학생 가입
        Student student = new Student("testID@gmail.com", "testPw", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        // 게시물 작성
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        Board board = boardService.post(boardAddForm, student);

        // 댓글 작성
        CommentAddForm parentCommentAddForm = new CommentAddForm(board.getId(), joinStudent.getId(), null, "반가워요!");
        Comment parentComment = commentService.addComment(parentCommentAddForm);

        // 답글 작성
        for (int i = 0; i < 5; i++) {
            commentService.addComment(new CommentAddForm(board.getId(), joinStudent.getId(), parentComment.getId(), "반가워요!"+i+i));
        }

        em.flush();
        em.clear();

        List<Comment> findComments = commentService.findComments(board.getId());
        System.out.println("findComments = " + findComments);

        for (Comment findComment : findComments) {
            for (Comment child : findComment.getChildren()) {
                child.getContent();
            }
        }

    }
}