package register.demo.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import register.demo.domain.comments.Comment;
import register.demo.domain.student.Student;
import register.demo.web.board.BoardForm;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class BoardH2Repository implements BoardRepository{

    private final EntityManager em;

    public Long savePost(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Boolean updatePost(Long id, BoardForm boardForm) {
        Board originBoard = em.find(Board.class, id);
        originBoard.setTitle(boardForm.getTitle());
        originBoard.setContent(boardForm.getContent());

        return true;
    }

    public Boolean deletePost(Long id) {
        Board board = em.find(Board.class, id);
        board.setIsDelete(true);

        return true;
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findByTitle(String title) {
        return em.createQuery("select b from Board b where b.title like concat('%', :title, '%')", Board.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Board> findByStudent(Student student) {
        return em.createQuery("select b from Board b where b.writer =: student", Board.class)
                .setParameter("student", student)
                .getResultList();
    }

    public List<Board> findAllPost() {
        return em.createQuery("select b from Board b where b.isDelete=:isDelete", Board.class)
                .setParameter("isDelete", false)
                .getResultList();
    }
}
