package register.demo.domain.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.like.PostLikeService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.dto.BoardUpdateDto;
import register.demo.web.board.dto.HotPostDto;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardUpdateForm;
import register.demo.web.board.search.SearchCondition;
import register.demo.web.board.search.SearchType;
import register.demo.web.postlike.dto.PostLikeDto;

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
        assertEquals("게시글0", title);
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
        assertEquals(2, boardService.findBoard(new SearchCondition("테스트", SearchType.TIT)).size());
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
        assertEquals(1, boardService.findBoard(new SearchCondition("테스터1", SearchType.STUD)).size());
        assertEquals(1, boardService.findBoard(new SearchCondition("테스터2", SearchType.STUD)).size());

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
        assertEquals(2, boardService.findBoards(PageRequest.of(0, 2)).getSize());
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

    @Test
    public void 검색() throws Exception {
        //given
        Student student1 = new Student("testID1@gmail.com", "testPW1", "테스터A", "테스터A", "컴공", "백엔드");
        Student studentA = studentService.join(student1);

        Student student2 = new Student("testID2@gmail.com", "testPW2", "테스터B", "테스터B", "컴공", "백엔드");
        Student studentB = studentService.join(student2);

        BoardAddForm boardAddForm1 = new BoardAddForm("반가워요~ 하이요!", "방가방가!", null, null);
        BoardPostDto boardPostDto1 = boardAddForm1.createBoardPostDto(studentA);
        boardService.post(boardPostDto1);

        BoardAddForm boardAddForm2 = new BoardAddForm("방가! 다시왔어요!", "오랜만이네요 :(", null, null);
        BoardPostDto boardPostDto2 = boardAddForm2.createBoardPostDto(studentB);
        boardService.post(boardPostDto2);


        //when
        List<Board> titleBasedSearch = boardService.findBoard(new SearchCondition("반가", SearchType.TIT));
        List<Board> nicknameBasedSearch = boardService.findBoard(new SearchCondition("테스터B", SearchType.STUD));
        List<Board> titleAndContentBasedSearch = boardService.findBoard(new SearchCondition("방가", SearchType.TITCONT));


        //then
        assertThat(titleBasedSearch).extracting("title").containsExactly("반가워요~ 하이요!");
        assertThat(nicknameBasedSearch).extracting("title").containsExactly("방가! 다시왔어요!");
        assertThat(titleAndContentBasedSearch).extracting("title").containsExactlyInAnyOrder("반가워요~ 하이요!", "방가! 다시왔어요!");
    }

    @Test
    public void 조인테스트() throws Exception {
        //given
        Student student = new Student("testID@gmail.com", "testPW", "테스터", "테스터", "컴공", "백엔드");
        Student joinStudent = studentService.join(student);

        //when
        BoardAddForm boardAddForm = new BoardAddForm("테스트 글", "테스트 글입니다.", null, null);
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
    @Commit
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
            BoardAddForm boardAddForm = new BoardAddForm("테스트 글" + i, "테스트 글입니다." + i, null, null);
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