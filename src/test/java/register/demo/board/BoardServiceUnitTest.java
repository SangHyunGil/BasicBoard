package register.demo.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;
import register.demo.board.domain.Board;
import register.demo.board.repository.BoardRepository;
import register.demo.board.service.BoardServiceImpl;
import register.demo.category.domain.Category;
import register.demo.category.repository.CategoryRepository;
import register.demo.category.domain.CategoryType;
import register.demo.comments.repository.CommentRepository;
import register.demo.file.service.AttachmentService;
import register.demo.student.domain.Student;
import register.demo.board.controller.dto.BoardPostDto;
import register.demo.board.controller.dto.BoardUpdateDto;
import register.demo.board.controller.form.BoardAddForm;
import register.demo.board.controller.form.BoardUpdateForm;
import register.demo.board.controller.search.SearchCondition;
import register.demo.board.controller.search.SearchType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTest {
    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    BoardRepository boardRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    AttachmentService attachmentService;

    @Mock
    CategoryRepository categoryRepository;

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

        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(student);

        Long CategoryId = 2L;
        Category category = new Category(CategoryType.BACK);
        ReflectionTestUtils.setField(category, "id", CategoryId);

        Long boardId = 2L;
        Board board = Board.builder()
                .writer(student)
                .title("테스트글")
                .content("테스트글입니다.")
                .category(category)
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        //mocking
        given(categoryRepository.save(any())).willReturn(category);
        given(boardRepository.save(any())).willReturn(board);

        //when
        Board findBoard = boardService.post(boardPostDto);

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

        BoardUpdateDto boardUpdateDto = boardUpdateForm.createBoardUpdateDto(boardId);

        //mocking
        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Boolean isUpdate = boardService.update(boardUpdateDto);

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
        willDoNothing().given(commentRepository).deleteByBoardId(boardId);
        willDoNothing().given(categoryRepository).delete(any());
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

        SearchCondition condition = new SearchCondition("테스트글", SearchType.TIT);

        //mocking
        given(boardRepository.findBoardByPaging(any())).willReturn(new PageImpl<>(new ArrayList<>(Arrays.asList(board))));

        //when
        Board findBoard = boardService.findBoards(PageRequest.of(0, 4)).getContent().get(0);

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

        SearchCondition condition = new SearchCondition("테스터", SearchType.STUD);

        //mocking
        given(boardRepository.search(eq(condition), any(Pageable.class))).willReturn(new PageImpl<>(new ArrayList<>(Arrays.asList(board))));

        //when
        Board findBoard = boardService.findBoards(condition, PageRequest.of(0, 4)).getContent().get(0);

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
        given(boardRepository.findBoardByPaging(any())).willReturn(new PageImpl<>(new ArrayList<>(Arrays.asList(board1, board2))));

        //when
        Page<Board> findBoards = boardService.findBoards(PageRequest.of(0, 2));

        //then
        assertEquals(2, findBoards.getSize());
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
                .hit(0)
                .build();
        ReflectionTestUtils.setField(board, "id", boardId);

        given(boardRepository.findById(boardId)).willReturn(Optional.ofNullable(board));

        //when
        Boolean updateResult = boardService.updateHit(boardId);

        //then
        assertEquals(true, updateResult);
    }
}
