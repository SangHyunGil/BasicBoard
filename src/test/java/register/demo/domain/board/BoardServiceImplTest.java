package register.demo.domain.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.BoardForm;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    StudentService studentService;
    @Autowired
    BoardService boardService;

    @Test
    public void 글등록() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);

        //when
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        Long postId = boardService.post(board);

        //then
        Board findBoard = boardService.findBoard(postId);
        assertEquals(board, findBoard);
    }
    
    @Test
    public void 글삭제() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);

        //when
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);

        Long postId = boardService.post(board);
        Boolean isDelete = boardService.delete(postId);

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void 글수정() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);

        //when
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        BoardForm boardForm = new BoardForm("테스트 글 수정", student.getNickname(), "테스트 글 수정했습니다.");

        Long postId = boardService.post(board);
        boardService.update(board.getId(), boardForm);

        //then
        assertEquals("테스트 글 수정했습니다.", boardService.findBoard(postId).getContent());
    }

    @Test
    public void ID_글조회() throws Exception {
        //given

        //when
        String title = boardService.findBoard(1L).getTitle();

        //then
        assertEquals("게시글1", title);
    }

    @Test
    public void 제목_글조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        studentService.join(student);

        //when
        Board board1 = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        Board board2 = new Board("게시글 테스트", student, "테스트 글입니다.", LocalDateTime.now(), false);
        Board board3 = new Board("테스트글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        Board board4 = new Board("게시글테스트", student, "테스트 글입니다.", LocalDateTime.now(), false);

        boardService.post(board1);
        boardService.post(board2);
        boardService.post(board3);
        boardService.post(board4);

        //then
        assertEquals(4, boardService.findBoard("테스트").size());
    }

    @Test
    public void 작성자_글조회() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터1", "테스터1", "컴공", "백엔드");
        Long student1Id = studentService.join(student1);
        Student studentA = studentService.findStudent(student1Id);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터2", "테스터2", "컴공", "백엔드");
        Long student2Id = studentService.join(student2);
        Student studentB = studentService.findStudent(student2Id);

        //when
        Board board1 = new Board("테스트 글", studentA, "테스트 글입니다.", LocalDateTime.now(), false);
        Board board2 = new Board("게시글 테스트", studentA, "테스트 글입니다.", LocalDateTime.now(), false);
        Board board3 = new Board("테스트글", studentB, "테스트 글입니다.", LocalDateTime.now(), false);
        Board board4 = new Board("게시글테스트", studentB, "테스트 글입니다.", LocalDateTime.now(), false);

        boardService.post(board1);
        boardService.post(board2);
        boardService.post(board3);
        boardService.post(board4);

        //then
        assertEquals(2, boardService.findBoard(studentA).size());
        assertEquals(2, boardService.findBoard(studentB).size());

    }
    
    @Test
    public void 전체글조회() throws Exception {
        //given
        Student student = new Student("testID1@gmail.com", "testPW1", "테스터1", "테스터1", "컴공", "백엔드");
        studentService.join(student);
                
        //when
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false);
        boardService.post(board);
        
        //then
        assertEquals(2, boardService.findBoards().size());
    }
}