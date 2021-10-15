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
@Slf4j
public class BoardH2Repository implements BoardRepository{

    private final EntityManager em;

    public Long savePost(Board board) {
        log.info("savePost");
        em.persist(board);
        return board.getId();
    }

    public Boolean updatePost(Long id, BoardForm boardForm) {
        log.info("updatePost");
        Board originBoard = em.find(Board.class, id);
        originBoard.setTitle(boardForm.getTitle());
        originBoard.setContent(boardForm.getContent());

        return true;
    }

    public Boolean deletePost(Long id) {
        log.info("deletePost");
        Board board = em.find(Board.class, id);
        board.setIsDelete(true);

        return true;
    }

    public Board findById(Long id) {
        log.info("BoardFindById");
        return em.find(Board.class, id);
    }

    public List<Board> findByTitle(String title) {
        log.info("BoardFindByTitle");
        return em.createQuery("select b from Board b where b.title like concat('%', :title, '%')", Board.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Board> findByStudent(Student student) {
        log.info("BoardFindByStudent");
        return em.createQuery("select b from Board b where b.writer =: student", Board.class)
                .setParameter("student", student)
                .getResultList();
    }

    public List<Board> findAllPost() {
        log.info("BoardFindAllPost");
        return em.createQuery("select b from Board b where b.isDelete=:isDelete", Board.class)
                .setParameter("isDelete", false)
                .getResultList();
    }
}
