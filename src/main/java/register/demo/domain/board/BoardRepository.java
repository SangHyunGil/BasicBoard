package register.demo.domain.board;

import register.demo.domain.student.Student;
import register.demo.web.board.BoardForm;

import java.util.List;

public interface BoardRepository {

    Long savePost(Board board);

    Boolean updatePost(Long postId, BoardForm boardForm);

    Boolean deletePost(Long boardId);

    Board findById(Long id);

    List<Board> findByTitle(String title);

    List<Board> findByStudent(Student student);

    List<Board> findAllPost();
}
