package register.demo.domain.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.domain.file.AttachmentService;
import register.demo.domain.student.Student;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardUpdateForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTest {
    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    BoardRepository boardRepository;

    @Mock
    AttachmentService attachmentService;

    @Test
    public void 글등록() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        BoardAddForm boardAddForm = BoardAddForm.builder()
                .title("테스트글")
                .content("테스트글입니다.")
                .build();

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(boardRepository.save(any())).willReturn(board);

        //when
        Board findBoard = boardService.post(boardAddForm, student);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 글수정() throws Exception {
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

        BoardUpdateForm boardUpdateForm = BoardUpdateForm.builder()
                .writer(student.getNickname())
                .title("테스트 글 수정")
                .content("테스트 글 수정했습니다.")
                .build();

        //mocking
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Boolean isUpdate = boardService.update(boardId, boardUpdateForm);

        //then
        assertEquals(true, isUpdate);
    }

    @Test
    public void 글삭제() throws Exception {
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

        //mocking
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Boolean isDelete = boardService.delete(boardId);

        //then
        assertEquals(true, isDelete);
    }

    @Test
    public void ID_글조회() throws Exception {
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

        //mocking
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Board findBoard = boardService.findBoard(boardId);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 제목_글조회() throws Exception {
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

        //mocking
        given(boardRepository.findByTitle("테스트 글")).willReturn(new ArrayList<>(Arrays.asList(board)));

        //when
        Board findBoard = boardService.findBoard("테스트 글").get(0);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 작성자_글조회() throws Exception {
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

        //mocking
        given(boardRepository.findByWriter(student)).willReturn(new ArrayList<>(Arrays.asList(board)));

        //when
        Board findBoard = boardService.findBoard(student).get(0);

        //then
        assertEquals(board, findBoard);
    }

    @Test
    public void 전체글조회() throws Exception {
        //given
        Long StudentId = 2L;
        Student student = Student.builder()
                .email("testID@gmail.com")
                .password("testPW")
                .nickname("테스터")
                .build();
        ReflectionTestUtils.setField(student, "id", StudentId);

        Long boardId1 = 2L;
        Board board1 = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board1, "id", boardId1);

        Long boardId2 = 3L;
        Board board2 = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .build();
        ReflectionTestUtils.setField(board2, "id", boardId2);



        //mocking
        given(boardRepository.findAll(Sort.by(Sort.Direction.DESC, "writeTime"))).willReturn(new ArrayList<>(Arrays.asList(board1, board2)));

        //when
        List<Board> findBoards = boardService.findBoards(Sort.by(Sort.Direction.DESC, "writeTime"));

        //then
        assertEquals(2, findBoards.size());
    }

    @Test
    public void 조회수() throws Exception {
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

        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Boolean updateResult = boardService.updateHit(boardId);

        //then
        assertEquals(true, updateResult);
    }
}
