package register.demo.comments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import register.demo.board.domain.Board;
import register.demo.board.service.BoardService;
import register.demo.category.domain.CategoryType;
import register.demo.comments.domain.Comment;
import register.demo.comments.service.CommentService;
import register.demo.student.domain.Student;
import register.demo.student.service.StudentService;
import register.demo.board.controller.dto.BoardPostDto;
import register.demo.board.controller.form.BoardAddForm;
import register.demo.comments.controller.dto.CommentAddDto;
import register.demo.comments.controller.form.CommentAddForm;

import javax.persistence.EntityManager;
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
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        CommentAddForm commentAddForm = new CommentAddForm("테스트 댓글입니다.", null);
        CommentAddDto commentAddDto = commentAddForm.createCommentAddDto(board.getId(), joinStudent);

        //when
        commentService.addComment(commentAddDto);

        //then
        assertEquals(1, commentService.findComments(board.getId()).size());
    }

    @Test
    public void 전체댓글조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        CommentAddForm commentAddForm1 = new CommentAddForm("테스트 댓글입니다.", null);
        CommentAddDto commentAddDto1 = commentAddForm1.createCommentAddDto(board.getId(), joinStudent);
        CommentAddForm commentAddForm2 = new CommentAddForm("테스트 댓글입니다.", null);
        CommentAddDto commentAddDto2 = commentAddForm2.createCommentAddDto(board.getId(), joinStudent);
        CommentAddForm commentAddForm3 = new CommentAddForm("테스트 댓글입니다.", null);
        CommentAddDto commentAddDto3 = commentAddForm3.createCommentAddDto(board.getId(), joinStudent);

        commentService.addComment(commentAddDto1);
        commentService.addComment(commentAddDto2);
        commentService.addComment(commentAddDto3);

        //when
        List<Comment> comments = commentService.findComments(board.getId());

        //then
        assertEquals(3, comments.size());
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        CommentAddForm commentAddForm = new CommentAddForm("테스트 댓글입니다.", null);
        CommentAddDto commentAddDto = commentAddForm.createCommentAddDto(board.getId(), joinStudent);

        //when
        Comment comment = commentService.addComment(commentAddDto);
        commentService.updateComment(comment.getId(), "테스트 댓글 수정하였습니다.");

        //then
        assertEquals("테스트 댓글 수정하였습니다.", commentService.findComments(board.getId()).get(0).getContent());
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        CommentAddForm commentAddForm = new CommentAddForm("테스트 댓글입니다.", null);
        CommentAddDto commentAddDto = commentAddForm.createCommentAddDto(board.getId(), joinStudent);

        //when
        commentService.addComment(commentAddDto);

        Comment findComment = commentService.findComments(board.getId()).get(0);
        Boolean isDelete = commentService.deleteComment(findComment.getId());

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void 답글달기() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        CommentAddForm parentAddForm = new CommentAddForm("부모 댓글입니다.", null);
        CommentAddDto parentAddDto = parentAddForm.createCommentAddDto(board.getId(), joinStudent);
        Comment parentComment = commentService.addComment(parentAddDto);

        //when
        CommentAddForm childAddForm = new CommentAddForm("자식 댓글입니다.", parentComment.getId());
        CommentAddDto childAddDto = childAddForm.createCommentAddDto(board.getId(), joinStudent);
        Comment childComment = commentService.addComment(childAddDto);

        //then
        assertEquals(parentComment, childComment.getParent());
    }

    @Test
    public void N_플러스_1_문제() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        // 댓글 작성
        CommentAddForm parentAddForm = new CommentAddForm("부모 댓글입니다.", null);
        CommentAddDto parentAddDto = parentAddForm.createCommentAddDto(board.getId(), joinStudent);
        Comment parentComment = commentService.addComment(parentAddDto);

        // 답글 작성
        for (int i = 0; i < 5; i++) {
            CommentAddForm childAddForm = new CommentAddForm(i+"번째 자식 댓글입니다.", parentComment.getId());
            CommentAddDto childAddDto = childAddForm.createCommentAddDto(board.getId(), joinStudent);
            commentService.addComment(childAddDto);
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