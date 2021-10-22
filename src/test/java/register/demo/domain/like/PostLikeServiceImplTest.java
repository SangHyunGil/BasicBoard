package register.demo.domain.like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.board.Board;
import register.demo.domain.board.BoardService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.postlike.dto.PostLikeDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostLikeServiceImplTest {

    @Autowired
    StudentService studentService;
    @Autowired
    BoardService boardService;
    @Autowired
    PostLikeService postLikeService;

    @Test
    public void 좋아요() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        //when
        PostLikeDto postLikeDto = new PostLikeDto(joinStudent, board.getId());
        postLikeService.pushLikeButton(postLikeDto);

        //then
        assertEquals(1, postLikeService.getPostLikeInfo(postLikeDto).getPostLikeNum());
    }

    @Test
    public void 좋아요취소() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터A", "테스터A", "컴공", "백엔드");
        Student joinStudentA = studentService.join(student1);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터B", "테스터B", "컴공", "백엔드");
        Student joinStudentB = studentService.join(student2);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudentA);
        Board board = boardService.post(boardPostDto);

        PostLikeDto postLikeDtoA = new PostLikeDto(joinStudentA, board.getId());
        postLikeService.pushLikeButton(postLikeDtoA);

        PostLikeDto postLikeDtoB = new PostLikeDto(joinStudentB, board.getId());
        postLikeService.pushLikeButton(postLikeDtoB);

        //when
        postLikeService.pushLikeButton(postLikeDtoB);

        //then
        assertEquals(1, postLikeService.getPostLikeInfo(postLikeDtoB).getPostLikeNum());
    }

    @Test
    public void 좋아요개수조회() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터A", "테스터A", "컴공", "백엔드");
        Student joinStudentA = studentService.join(student1);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터B", "테스터B", "컴공", "백엔드");
        Student joinStudentB = studentService.join(student2);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudentA);
        Board board = boardService.post(boardPostDto);

        PostLikeDto postLikeDtoA = new PostLikeDto(joinStudentA, board.getId());
        postLikeService.pushLikeButton(postLikeDtoA);

        PostLikeDto postLikeDtoB = new PostLikeDto(joinStudentB, board.getId());
        postLikeService.pushLikeButton(postLikeDtoB);

        //when
        long postLikeNum = postLikeService.getPostLikeInfo(postLikeDtoB).getPostLikeNum();

        //then
        assertEquals(2, postLikeNum);
    }

    @Test
    public void 좋아요여부확인() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        //when
        PostLikeDto postLikeDto = new PostLikeDto(joinStudent, board.getId());
        postLikeService.pushLikeButton(postLikeDto);
        Boolean check = postLikeService.getPostLikeInfo(postLikeDto).getCheckLiked();

        //then
        assertEquals(true, check);
    }
}