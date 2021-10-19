package register.demo.domain.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.domain.student.Student;
import register.demo.web.board.BoardForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTest {
    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    BoardRepository boardRepository;

    @Test
    public void 글등록() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(boardRepository.save(board)).willReturn(board);

        //when
        Board findBoard = boardService.post(board);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 글수정() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);
        BoardForm boardForm = new BoardForm("테스트 글 수정", student.getNickname(), "테스트 글 수정했습니다.");

        //mocking
        given(boardRepository.save(board)).willReturn(board);
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Board findBoard = boardService.post(board);
        Boolean isUpdate = boardService.update(boardId, boardForm);

        //then
        assertEquals(true, isUpdate);
    }

    @Test
    public void 글삭제() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(boardRepository.save(board)).willReturn(board);
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        boardService.post(board);
        Boolean isDelete = boardService.delete(boardId);

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void ID_글조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(boardRepository.save(board)).willReturn(board);
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        boardService.post(board);
        Board findBoard = boardService.findBoard(boardId);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 제목_글조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(boardRepository.save(board)).willReturn(board);
        given(boardRepository.findByTitle("테스트 글")).willReturn(new ArrayList<>(Arrays.asList(board)));

        //when
        boardService.post(board);
        Board findBoard = boardService.findBoard("테스트 글").get(0);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 작성자_글조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId = 2L;
        Board board = new Board("테스트 글", student, "테스트 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(boardRepository.save(board)).willReturn(board);
        given(boardRepository.findByWriter(student)).willReturn(new ArrayList<>(Arrays.asList(board)));

        //when
        boardService.post(board);
        Board findBoard = boardService.findBoard(student).get(0);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 전체글조회() throws Exception {
        //given
        Long studentId = 2L;
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        ReflectionTestUtils.setField(student, "id", studentId);

        Long boardId1 = 2L;
        Board board1 = new Board("테스트 글1", student, "테스트1 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board1, "id", boardId1);

        Long boardId2 = 3L;
        Board board2 = new Board("테스트 글2", student, "테스트2 글입니다.", LocalDateTime.now(), false, 0);
        ReflectionTestUtils.setField(board2, "id", boardId2);

        //mocking
        given(boardRepository.save(board1)).willReturn(board1);
        given(boardRepository.save(board2)).willReturn(board2);
        given(boardRepository.findAll(Sort.by(Sort.Direction.DESC, "writeTime"))).willReturn(new ArrayList<>(Arrays.asList(board1, board2)));

        //when
        boardService.post(board1);
        boardService.post(board2);
        List<Board> findBoards = boardService.findBoards(Sort.by(Sort.Direction.DESC, "writeTime"));

        //then
        assertEquals(2, findBoards.size());

    }
}
