package register.demo.domain.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.dto.BoardUpdateDto;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardUpdateForm;

import java.time.LocalDateTime;

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
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        //then
        Board findBoard = boardService.findBoard(board.getId());
        assertEquals(board, findBoard);
    }
    
    @Test
    public void 글삭제() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);
        Boolean isDelete = boardService.delete(board.getId());

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void 글수정() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        BoardUpdateForm boardUpdateForm = new BoardUpdateForm("테스트 글 수정", student.getNickname(), "테스트 글 수정했습니다.");
        BoardUpdateDto boardUpdateDto = boardUpdateForm.createBoardUpdateDto(board.getId());
        boardService.update(boardUpdateDto);

        //then
        assertEquals("테스트 글 수정했습니다.", boardService.findBoard(board.getId()).getContent());
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
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm1 = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto1 = boardAddForm1.createBoardPostDto(joinStudent);
        BoardAddForm boardAddForm2 = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto2 = boardAddForm2.createBoardPostDto(joinStudent);

        boardService.post(boardPostDto1);
        boardService.post(boardPostDto2);

        //then
        assertEquals(2, boardService.findBoard("테스트").size());
    }

    @Test
    public void 작성자_글조회() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터1", "테스터1", "컴공", "백엔드");
        Student studentA = studentService.join(student1);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터2", "테스터2", "컴공", "백엔드");
        Student studentB = studentService.join(student2);

        //when
        BoardAddForm boardAddForm1 = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto1 = boardAddForm1.createBoardPostDto(studentA);
        BoardAddForm boardAddForm2 = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto2 = boardAddForm2.createBoardPostDto(studentB);

        boardService.post(boardPostDto1);
        boardService.post(boardPostDto2);

        //then
        assertEquals(1, boardService.findBoard(studentA).size());
        assertEquals(1, boardService.findBoard(studentB).size());

    }
    
    @Test
    public void 전체글조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);
        
        //then
        assertEquals(2, boardService.findBoards(Sort.by(Sort.Direction.DESC, "writeTime")).size());
    }

    @Test
    public void 조회수() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        //when
        boardService.updateHit(board.getId());

        //then
        assertEquals(1, board.getHit());
    }
}