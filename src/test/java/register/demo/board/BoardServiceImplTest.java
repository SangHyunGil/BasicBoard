package register.demo.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import register.demo.board.domain.Board;
import register.demo.board.service.BoardService;
import register.demo.category.domain.CategoryType;
import register.demo.like.service.PostLikeService;
import register.demo.student.domain.Student;
import register.demo.student.service.StudentService;
import register.demo.board.controller.dto.BoardPostDto;
import register.demo.board.controller.dto.BoardUpdateDto;
import register.demo.board.controller.dto.HotPostDto;
import register.demo.board.controller.form.BoardAddForm;
import register.demo.board.controller.form.BoardUpdateForm;
import register.demo.board.controller.search.SearchCondition;
import register.demo.board.controller.search.SearchType;
import register.demo.like.controller.dto.PostLikeDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceImplTest {
    @Autowired
    EntityManager em;
    @Autowired
    StudentService studentService;
    @Autowired
    BoardService boardService;
    @Autowired
    PostLikeService postLikeService;

    @Test
    public void 글등록() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
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
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
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
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
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
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        BoardUpdateForm boardUpdateForm = new BoardUpdateForm("테스트 글 수정", student.getNickname(), "테스트 글 수정했습니다.");
        BoardUpdateDto boardUpdateDto = boardUpdateForm.createBoardUpdateDto(board.getId());
        boardService.update(boardUpdateDto);
        String title = boardService.findBoard(board.getId()).getTitle();

        //then
        assertEquals("테스트 글 수정", title);
    }

    @Test
    public void 제목_글조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm1 = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto1 = boardAddForm1.createBoardPostDto(joinStudent);
        BoardAddForm boardAddForm2 = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto2 = boardAddForm2.createBoardPostDto(joinStudent);

        boardService.post(boardPostDto1);
        boardService.post(boardPostDto2);

        //then
        assertEquals(2, boardService.findBoards(new SearchCondition("테스트", SearchType.TIT), PageRequest.of(0, 4)).getContent().size());
    }

    @Test
    public void 작성자_글조회() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터1", "테스터1", "컴공", "백엔드");
        Student studentA = studentService.join(student1);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터2", "테스터2", "컴공", "백엔드");
        Student studentB = studentService.join(student2);

        //when
        BoardAddForm boardAddForm1 = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto1 = boardAddForm1.createBoardPostDto(studentA);
        BoardAddForm boardAddForm2 = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto2 = boardAddForm2.createBoardPostDto(studentB);

        boardService.post(boardPostDto1);
        boardService.post(boardPostDto2);

        //then
        assertEquals(1, boardService.findBoards(new SearchCondition("테스터1", SearchType.STUD), PageRequest.of(0, 4)).getContent().size());
        assertEquals(1, boardService.findBoards(new SearchCondition("테스터2", SearchType.STUD), PageRequest.of(0, 4)).getContent().size());

    }
    
    @Test
    public void 전체글조회() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);
        
        //then
        assertEquals(2, boardService.findBoards(PageRequest.of(0, 2)).getSize());
    }

    @Test
    public void 조회수() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        Board board = boardService.post(boardPostDto);

        //when
        boardService.updateHit(board.getId());

        //then
        assertEquals(1, board.getHit());
    }

    @Test
    public void 검색() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터A", "테스터A", "컴공", "백엔드");
        Student studentA = studentService.join(student1);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터B", "테스터B", "컴공", "백엔드");
        Student studentB = studentService.join(student2);

        BoardAddForm boardAddForm1 = new BoardAddForm("테스트 글A", "테스트A 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto1 = boardAddForm1.createBoardPostDto(studentA);
        boardService.post(boardPostDto1);

        BoardAddForm boardAddForm2 = new BoardAddForm("테스트 글B", "테스트B 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto2 = boardAddForm2.createBoardPostDto(studentB);
        boardService.post(boardPostDto2);


        //when
        Page<Board> titleBasedSearch = boardService.findBoards(new SearchCondition("테스트 글A", SearchType.TIT), PageRequest.of(0, 4));
        Page<Board> nicknameBasedSearch = boardService.findBoards(new SearchCondition("테스터A", SearchType.STUD), PageRequest.of(0, 4));
        Page<Board> titleAndContentBasedSearch = boardService.findBoards(new SearchCondition("테스트", SearchType.TITCONT), PageRequest.of(0, 4));


        //then
        assertThat(titleBasedSearch.getContent()).extracting("title").containsExactly("테스트 글A");
        assertThat(nicknameBasedSearch.getContent()).extracting("title").containsExactly("테스트 글A");
        assertThat(titleAndContentBasedSearch.getContent()).extracting("title").containsExactlyInAnyOrder("테스트 글A", "테스트 글B");
    }

    @Test
    public void 조인테스트() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
        BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(joinStudent);
        boardService.post(boardPostDto);

        em.flush();
        em.clear();

        //then
        List<String> resultList = em.createQuery(
                        "select w.nickname from Board b " +
                                "join b.writer w", String.class)
                .getResultList();
    }

    @Test
    public void 인기게시물() throws Exception {
        //given
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student student = new Student("testID" + i + "@gmail.com", "testPW" + i, "테스터" + i, "테스터" + i, "컴공", "백엔드");
            Student joinStudent = studentService.join(student);
            students.add(joinStudent);
        }

        //when
        List<Board> boards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", CategoryType.BACK, null, null);
            BoardPostDto boardPostDto = boardAddForm.createBoardPostDto(students.get(i));
            Board post = boardService.post(boardPostDto);
            boards.add(post);
        }

        for (int i = 0; i < 5; i+=2) {
            for (int j = 0; j < 4; j++) {
                PostLikeDto postLikeDto = new PostLikeDto(students.get(j), boards.get(i).getId());
                postLikeService.pushLikeButton(postLikeDto);
            }
        }

        //then
        List<HotPostDto> hotPosts = boardService.findHotPosts();
        assertThat(hotPosts).extracting("num").containsExactly(4L, 4L, 4L);
    }
}